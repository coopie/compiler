package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Str extends GeneralArmInstruction implements PseudoInstruction {

	public Str(Operand source, Operand dest) {
		super(dest, source);
	}

	public Str(Operand source, Operand dest, Condition cond) {
		super(dest, source, cond);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
