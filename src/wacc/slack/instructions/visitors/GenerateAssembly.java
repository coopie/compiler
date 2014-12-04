package wacc.slack.instructions.visitors;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Eor;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

public class GenerateAssembly implements InstructionVistor<String> {

	IgnoringTemporariesVisitor printOperand;

	GenerateAssembly() {

	}

	private String newLine(int indent) {
		String s = "\n";
		for (int i = 0; i < indent; i++) {
			s += "\t";
		}
		return s;
	}

	@Override
	public String visit(Mov mov) {
		printOperand.setImmediateValuePrefix("#");
		return newLine(4) + "MOV" + mov.getCond() + " "
				+ mov.getDest().accept(printOperand) + ", "
				+ mov.getSource().accept(printOperand);
	}

	@Override
	public String visit(Label label) {
		return newLine(2) + label.getName() + ":";
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
		// Because ldr uses =immediatevalue instead of #immediatevalue
		String result = newLine(4) + "LDR" + ldr.getCond() + " "
				+ ldr.getDest().accept(printOperand) + ", ";
		printOperand.setImmediateValuePrefix("=");
		result += ldr.getSource().accept(printOperand);
		printOperand.setImmediateValuePrefix("#");
		return result;
	}

	@Override
	public String visit(BLInstruction blInstruction) {
		return newLine(4) + "BL" + blInstruction.getCond() + " "
				+ blInstruction.getLabel();
	}

	@Override
	public String visit(Pop pop) {
		return newLine(4) + "POP {" + pop.getOperand().accept(printOperand)
				+ "}";
	}

	@Override
	public String visit(Push push) {
		return newLine(4) + "PUSH {" + push.getOperand().accept(printOperand)
				+ "}";
	}

	@Override
	public String visit(Cmp cmp) {
		return newLine(4) + "CMP" + cmp.getCond() + " "
				+ cmp.getDest().accept(printOperand) + ", "
				+ cmp.getSource().accept(printOperand);
	}

	@Override
	public String visit(BranchInstruction b) {
		return newLine(4) + "B" + b.getCond() + " " + b.getLabel();
	}

	@Override
	public String visit(And and) {
		return concatOperands("AND", and.getCond(), and.getDest(),
				and.getSource(), and.getSource2());
	}

	@Override
	public String visit(Orr or) {
		return concatOperands("ORR", or.getCond(), or.getDest(),
				or.getSource(), or.getSource2());
	}

	@Override
	public String visit(Mul mul) {
		return concatOperands("MUL", mul.getCond(), mul.getDest(),
				mul.getSource(), mul.getSource2());
	}

	@Override
	public String visit(Add add) {
		return concatOperands("ADD", add.getCond(), add.getDest(),
				add.getSource(), add.getSource2());
	}

	@Override
	public String visit(Sub sub) {
		return concatOperands("SUB", sub.getCond(), sub.getDest(),
				sub.getSource(), sub.getSource2());
	}

	private String concatOperands(String instr, Condition cond, Operand dest,
			Operand source, Operand source2) {

		String base = newLine(4) + instr + cond + " " + dest.accept(printOperand) + ", "
				+ source.accept(printOperand);
		String source2String = source2.accept(printOperand);
		if (source2String == "") {
			return base;
		} else {
			return base + ", " + source2String;
		}

	}

	@Override
	public String visit(Str str) {
		// Because str uses =immediatevalue instead of #immediatevalue
		String result = newLine(4) + "STR" + str.getCond() + " "
				+ str.getSource().accept(printOperand) + ", ";
		printOperand.setImmediateValuePrefix("=");
		result += str.getDest().accept(printOperand);
		printOperand.setImmediateValuePrefix("#");
		return result;
	}

	@Override
	public String visit(Eor eor) {
		return concatOperands("EOR", eor.getCond(), eor.getDest(),
				eor.getSource(), eor.getSource2());
	}

}
