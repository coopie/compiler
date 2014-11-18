package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public class BLInstruction implements PseudoInstruction {

	private final String string;

	public BLInstruction(String string) {
		this.string = string;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public String getLabel() {
		return string;
	}

}
