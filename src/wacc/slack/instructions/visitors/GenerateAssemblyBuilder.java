package wacc.slack.instructions.visitors;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.interferenceGraph.InterferenceGraph;
import wacc.slack.interferenceGraph.InterferenceGraphColourer;

public class GenerateAssemblyBuilder {

	private class IgnoringTemporariesVisitor implements OperandVisitor<String> {

		private String immediateValuePrefix = "#";

		@Override
		public void setImmediateValuePrefix(String prefix) {
			immediateValuePrefix = prefix;
		}

		@Override
		public String visit(ArmRegister realRegister) {
			return realRegister.name();
		}

		@Override
		public String visit(TemporaryRegister temporaryRegister) {
			return "R" + temporaryRegister.getN();
		}

		@Override
		public String visit(Label label) {
			return "=" + label.getName();
		}

		@Override
		public String visit(ImmediateValue immediateValue) {
			String result = immediateValue.getValue(immediateValuePrefix);
			// You only need to set it for things that aren't # and it reverts
			// back after you use it
			setImmediateValuePrefix("#");
			return result;
		}

		@Override
		public String visit(Address address) {
			if (address.getOffset() == 0) {
				return "[" + address.getRegister().accept(this) + "]";
			} else {
				return "[" + address.getRegister().accept(this) + ", #"
						+ address.getOffset() + "]";
			}
		}
	}

	private OperandVisitor<String> printOperand = new IgnoringTemporariesVisitor() {
		@Override
		public String visit(TemporaryRegister temporaryRegister) {
			return "T" + temporaryRegister.getN();
		}
	};

	private Deque<PseudoInstruction> intermediateCode;
	private int optimizationLevel = 0;

	public GenerateAssemblyBuilder ignoringTemporaries() {
		printOperand = new IgnoringTemporariesVisitor();
		return this;
	}

	public GenerateAssembly make() {
		GenerateAssembly gen = new GenerateAssembly();
		final Map<Register, ArmRegister> mapping = new HashMap<>();

		if (optimizationLevel > 0) {
			ControlFlowGraph cfg = new ControlFlowGraph(intermediateCode);
			InterferenceGraph ig = new InterferenceGraph(cfg);
			InterferenceGraphColourer igc = new InterferenceGraphColourer(ig);
			igc.generateTemporaryRegisterMappings(mapping);
			printOperand = new IgnoringTemporariesVisitor() {
				@Override
				public String visit(TemporaryRegister temporaryRegister) {
					ArmRegister r = mapping.get(temporaryRegister);
					if (r == null)
						throw new RuntimeException(
								"no mapping for temporary register found");
					return visit(r);
				}
			};
		}

		gen.printOperand = printOperand;
		return gen;
	}

	public GenerateAssemblyBuilder withIntermediateCode(
			Deque<PseudoInstruction> intermediateCode) {
		this.intermediateCode = intermediateCode;
		return this;
	}

	public GenerateAssemblyBuilder withOptimisationLevel(int i) {
		this.optimizationLevel = i;
		return this;
	}
}
