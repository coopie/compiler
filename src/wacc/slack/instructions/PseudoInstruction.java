package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public interface PseudoInstruction {

	<T> T accept(InstructionVistor<T> visitor);
}
