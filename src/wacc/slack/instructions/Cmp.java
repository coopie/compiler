package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Cmp extends GeneralArmInstruction implements PseudoInstruction {
	
	public Cmp(Operand op1, Operand op2) {
		super(op1, op2);
	}

	public Cmp(Operand op1, Operand op2, Condition cond) {
		super(op1, op2, cond);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}
	
}
