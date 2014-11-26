package wacc.slack.instructions.visitors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
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
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

//TDD class
public class GetUsedRegisters implements InstructionVistor<List<Register>> {

	private final class GetRegsIfAny implements OperandVisitor<List<Register>> {
		@Override
		public List<Register> visit(ArmRegister realRegister) {
			return new LinkedList<Register>(
					Arrays.asList((Register) realRegister));
		}

		@Override
		public List<Register> visit(TemporaryRegister temporaryRegister) {
			return new LinkedList<Register>(
					Arrays.asList((Register) temporaryRegister));
		}

		@Override
		public List<Register> visit(Label label) {
			return new LinkedList<Register>();
		}

		@Override
		public List<Register> visit(ImmediateValue immediateValue) {
			return new LinkedList<Register>();
		}
	}

	@Override
	public List<Register> visit(And and) {
		List<Register> l = and.getSource().accept(new GetRegsIfAny());
		l.addAll(and.getSource2().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(Orr or) {
		List<Register> l = or.getSource().accept(new GetRegsIfAny());
		l.addAll(or.getSource2().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(Mov mov) {
		return mov.getSource().accept(new GetRegsIfAny());
	}

	@Override
	public List<Register> visit(Label label) {
		return label.accept(new GetRegsIfAny());
	}

	@Override
	public List<Register> visit(AssemblerDirective assemblerDirective) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Swi swi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Ldr ldr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(BLInstruction blInsturction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Pop pop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Push push) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Cmp cmp) {
		List<Register> l = cmp.getSource().accept(new GetRegsIfAny());
		l.addAll(cmp.getDest().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(Mul mul) {
		List<Register> l = mul.getSource().accept(new GetRegsIfAny());
		l.addAll(mul.getSource2().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(Add add) {
		List<Register> l = add.getSource().accept(new GetRegsIfAny());
		l.addAll(add.getSource2().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(Sub sub) {
		List<Register> l = sub.getSource().accept(new GetRegsIfAny());
		l.addAll(sub.getSource2().accept(new GetRegsIfAny()));
		return l;
	}

	@Override
	public List<Register> visit(BranchInstruction b) {
		return b.getLabel().accept(new GetRegsIfAny());
	}

	@Override
	public List<Register> visit(Str str) {
		// Unsure about this implementation, from my understanding the Str instruction does not modify 
		// the values in either registers but reads from both
		List<Register> l = str.getSource().accept(new GetRegsIfAny());
		l.addAll(str.getDest().accept(new GetRegsIfAny()));
		return l;
	}

}
