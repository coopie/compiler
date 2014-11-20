package wacc.slack.instructions.visitors;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

public class GenerateAssembly implements InstructionVistor<String> {
	
	private String newLine(int indent) {
		String s = "\n";
		for(int i = 0; i < indent; i++) {
			s += "\t";
		}
		return s;
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
			
			return "=" + label.getName();
		}

		@Override
		public String visit(ImmediateValue immediateValue) {
			return immediateValue.getValue();
		}
		
	};
	
	@Override
	public String visit(Mov mov) {
		return newLine(4) +"MOV " + mov.getDest().accept(printOperand) + ", " +
				mov.getSource().accept(printOperand);
	}

	@Override
	public String visit(Label label) {
		return "\n" + newLine(2) + label.getName() + ":";
	}

	@Override
	public String visit(AssemblerDirective assemblerDirective) {
		return newLine(2) + assemblerDirective.getDirective();
	}

	@Override
	public String visit(Swi swi) {
		return newLine(4) + "SWI 0";
	}

	@Override
	public String visit(Ldr ldr) {
		return newLine(4) + "LDR " + ldr.getDest().accept(printOperand) +
				", " + ldr.getSource().accept(printOperand);
	}

	@Override
	public String visit(BLInstruction blInsturction) {
		return newLine(4) + "BL " + blInsturction.getLabel();
	}

	@Override
	public String visit(Pop pop) {
		return newLine(4) + "POP {" + pop.getOperand().accept(printOperand) + "}";
	}

	@Override
	public String visit(Push push) {
		return newLine(4) + "PUSH {" + push.getOperand().accept(printOperand) + "}";
	}

	@Override
	public String visit(Cmp cmp) {
		return newLine(4) + "CMP " + cmp.getSource().accept(printOperand) + ", " + cmp.getDest().accept(printOperand);
	}
	
	@Override
	public String visit(BranchInstruction b) {
		return newLine(4) + "B" + b.getCond() + " " + b.getLabel();
	}

	@Override
	public String visit(And and) {
		return newLine(4) + "AND " + and.getDest() + ", " + and.getSource() + ", " + and.getSource2();
	}

	@Override
	public String visit(Orr or) {
		return newLine(4) + "ORR " + or.getDest() + ", " + or.getSource() + ", " + or.getSource2();
	}

	@Override
	public String visit(Mul mul) {
		return newLine(4) + "MUL " + mul.getDest() + ", " + mul.getSource() + ", " + mul.getSource2();
	}

	@Override
	public String visit(Add add) {
		return newLine(4) + "ADD " + add.getDest() + ", " + add.getSource() + ", " + add.getSource2();
	}

	@Override
	public String visit(Sub sub) {
		return newLine(4) + "SUB " + sub.getDest() + ", " + sub.getSource() + ", " + sub.getSource2();
	}

}
