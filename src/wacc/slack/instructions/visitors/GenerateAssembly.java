package wacc.slack.instructions.visitors;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Swi;

public class GenerateAssembly implements InstructionVistor<String> {

	private OperandVisitor<String> printOperand = new OperandVisitor<String>(){

		@Override
		public String visit(ArmRegister realRegister) {
			return realRegister.name();
		}

		@Override
		public String visit(TemporaryRegister temporaryRegister) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Label label) {
			return "=" + label.getName();
		}

		@Override
		public String visit(ImmediateValue immediateValue) {
			return immediateValue.getValue();
		}
		
	};
	
	@Override
	public String visit(Mov mov) {
		return "MOV " + mov.getDest().accept(printOperand) + ", " + mov.getSource().accept(printOperand) + "\n";
	}

	@Override
	public String visit(Label label) {
		return label.getName() + ":\n";
	}

	@Override
	public String visit(AssemblerDirective assemblerDirective) {
		return assemblerDirective.getDirective() + "\n";
	}

	@Override
	public String visit(Swi swi) {
		return "swi 0\n";
	}

	@Override
	public String visit(Ldr ldr) {
		return "LDR " + ldr.getDest().accept(printOperand) + ", " + ldr.getSource().accept(printOperand) + "\n";
	}

	@Override
	public String visit(BLInstruction blInsturction) {
		return "BL " + blInsturction.getLabel();
	}

	@Override
	public String visit(Cmp cmp) {
		return "CMP " + cmp.getOp1().accept(printOperand) + ", " + cmp.getOp2().accept(printOperand) + "\n";
	}

}
