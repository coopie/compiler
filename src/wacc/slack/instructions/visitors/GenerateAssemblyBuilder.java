package wacc.slack.instructions.visitors;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.interferenceGraph.InterferenceGraph;
import wacc.slack.interferenceGraph.InterferenceGraphColourer;

public class GenerateAssemblyBuilder {

	private IgnoringTemporariesVisitor printOperand = new IgnoringTemporariesVisitor() {
		@Override
		public String visit(TemporaryRegister temporaryRegister) {
			return "T" + temporaryRegister.getN();
		}
	};

	private int optimizationLevel = 0;

	public GenerateAssemblyBuilder ignoringTemporaries() {
		printOperand = new IgnoringTemporariesVisitor();
		return this;
	}

	public GenerateAssembly make() {
		GenerateAssembly gen = new GenerateAssembly();

		if (optimizationLevel > 0) {
			printOperand = new IgnoringTemporariesVisitor() {
				@Override
				public String visit(TemporaryRegister temporaryRegister) {
					throw new RuntimeException("found temporary register, when they should be filtered out already");
				}
			};
		}

		gen.printOperand = printOperand;
		return gen;
	}

	public GenerateAssemblyBuilder withOptimisationLevel(int i) {
		this.optimizationLevel = i;
		return this;
	}
}
