package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public class BLInstruction implements PseudoInstruction {

	private final String string;
	private Condition cond = Condition.AL;
	
	public BLInstruction(String string) {
		this(string,Condition.AL);
	}
	
	public BLInstruction(String string, Condition cond) {
		this.string = Label.gasComforomLabel(string);
		this.cond = cond;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public String getLabel() {
		return string;
	}

	public Condition getCond() {
		return cond;
	}
	
}
