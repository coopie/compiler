package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Mov extends GeneralArmInstruction implements PseudoInstruction {
	
	public Mov(Operand dest, Operand source) {
		super(dest, source);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
