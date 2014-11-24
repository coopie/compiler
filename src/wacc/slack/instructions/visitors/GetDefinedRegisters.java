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
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

public class GetDefinedRegisters implements InstructionVistor<List<Register>> {

	private final class GetRegsIfAny implements OperandVisitor<List<Register>> {
		@Override
		public List<Register> visit(ArmRegister realRegister) {
			return Arrays.asList((Register)realRegister);
		}

		@Override
		public List<Register> visit(TemporaryRegister temporaryRegister) {
			return Arrays.asList((Register)temporaryRegister);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Orr or) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Mov mov) {
		return mov.getDest().accept(new GetRegsIfAny());
	}

	@Override
	public List<Register> visit(Label label) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Mul mul) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Add add) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(Sub sub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Register> visit(BranchInstruction b) {
		// TODO Auto-generated method stub
		return null;
	}

}
