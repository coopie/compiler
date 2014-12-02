package wacc.slack.instructions.visitors;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.Operand;
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
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

// only treats all temporary registers as load/store before the command they are used
public class SimpleRegisterAllocator implements InstructionVistor<List<PseudoInstruction>> {

	//currently we need 3 registers, these registers may well need to be changed to combat problems with 
	// other uses
	private final Register RA = ArmRegister.r7;
	private final Register RB = ArmRegister.r8;
	private final Register RC = ArmRegister.r9;
	
	@Override
	public List<PseudoInstruction> visit(And and) {
		List<PseudoInstruction> realCode = new LinkedList<PseudoInstruction>();
		
		realCode.addAll(loadRegs(and.getDest(), and.getSource(), and.getSource2()));
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Orr or) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Mov mov) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Label label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(AssemblerDirective assemblerDirective) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Swi swi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Ldr ldr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(BLInstruction blInsturction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Pop pop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Push push) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Cmp cmp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Mul mul) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Add add) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Sub sub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(BranchInstruction b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(Str str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<PseudoInstruction> loadRegs(Operand... regs) {
		for (Operand reg : regs) {
		}
		return null;
	}
	
	private List<PseudoInstruction> storeRegs(Operand... regs) {
		for (Operand reg : regs) {
		}
		return null;
	}
	
	private boolean isTemporary(Register r) {
		return (r.accept(new OperandVisitor<Boolean>() {

			@Override
			public Boolean visit(ArmRegister realRegister) {
				return false;
			}

			@Override
			public Boolean visit(TemporaryRegister temporaryRegister) {
				return true;
			}

			@Override
			public Boolean visit(Label label) {
				return false;
			}

			@Override
			public Boolean visit(ImmediateValue immediateValue) {
				return false;
			}

			@Override
			public Boolean visit(Address address) {
				return address.getRegister().accept(this);
			}
		}));
	}
	

}
