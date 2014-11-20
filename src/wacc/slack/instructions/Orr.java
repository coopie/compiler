package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Orr extends GeneralArmInstruction implements PseudoInstruction {

	public Orr(Operand dest, Operand source, Operand source2) {
		super(dest, source, source2);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
