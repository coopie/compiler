package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public class Label  implements PseudoInstruction {

	private final PseudoInstruction pi;
	private final String name;
	
	public Label(String name, PseudoInstruction pi) {
		this.pi = pi;
		this.name = name;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.accept(this);
	}

	public PseudoInstruction getInstruction() {
		return pi;
	}
	
	public String getName() {
		return name;
	}

}
