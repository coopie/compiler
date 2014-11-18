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
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Swi;

public class GenerateAssembly implements InstructionVistor<String> {
	
	private int indents = 0;
	
	private void incIndent() {
		indents++;
	}
	
	private void decIndent() {
		indents--;
	}
	
	private String newLine() {
		String newLine = "\n";
		
		for(int i = 0; i < indents; i++) {
			newLine.concat("\t");		
		}
		return newLine;
	}

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
			if(indents == 1) {
				decIndent();
			}
			incIndent();
			return "=" + label.getName() + newLine();
		}

		@Override
		public String visit(ImmediateValue immediateValue) {
			return immediateValue.getValue();
		}
		
	};
	
	@Override
	public String visit(Mov mov) {
		return "MOV " + mov.getDest().accept(printOperand) + ", " + mov.getSource().accept(printOperand) + newLine();
	}

	@Override
	public String visit(Label label) {
		return label.getName() + ":" + newLine();
	}

	@Override
	public String visit(AssemblerDirective assemblerDirective) {
		return assemblerDirective.getDirective() + newLine();
	}

	@Override
	public String visit(Swi swi) {
		return "SWI 0" + newLine();
	}

	@Override
	public String visit(Ldr ldr) {
		return "LDR " + ldr.getDest().accept(printOperand) + ", " + ldr.getSource().accept(printOperand) + newLine();
	}

	@Override
	public String visit(BLInstruction blInsturction) {
		return "BL " + blInsturction.getLabel() + newLine();
	}

	@Override
	public String visit(Pop pop) {
		return "POP {" + pop.getOperand().accept(printOperand) + "}" + newLine();
	}

	@Override
	public String visit(Push push) {
		return "PUSH {" + push.getOperand().accept(printOperand) + "}" + newLine();
	}

	@Override
	public String visit(Cmp cmp) {
		return "CMP " + cmp.getOp1().accept(printOperand) + ", " + cmp.getOp2().accept(printOperand) + "\n";
	}

}
