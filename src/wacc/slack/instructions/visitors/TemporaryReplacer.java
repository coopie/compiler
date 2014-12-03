package wacc.slack.instructions.visitors;

import java.util.LinkedList;
import java.util.Map;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Eor;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

public class TemporaryReplacer implements InstructionVistor<LinkedList<PseudoInstruction>> {

	private final Map<Register, ArmRegister> mapping;
	private final Map<Register, Boolean> isSpilled;

	public TemporaryReplacer(Map<Register, ArmRegister> mapping) {
		this.mapping = mapping;
		isSpilled = null;
	}
	
	

	@Override
	public LinkedList<PseudoInstruction> visit(And and) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Orr or) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Mov mov) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Label label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(
			AssemblerDirective assemblerDirective) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Swi swi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Ldr ldr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(BLInstruction blInsturction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Pop pop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Push push) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Cmp cmp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Mul mul) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Add add) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Sub sub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(BranchInstruction b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<PseudoInstruction> visit(Str str) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public LinkedList<PseudoInstruction> visit(Eor eor) {
		// TODO Auto-generated method stub
		return null;
	}
}
