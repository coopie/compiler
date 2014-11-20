package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public class BranchInstruction implements PseudoInstruction {
	
	private final Condition cond;
	private final Label label;
	
	public BranchInstruction(Condition cond, Label label) {
		this.cond = cond;
		this.label = label;
	}
	
	public Condition getCond() {
		return cond;
	}
	
	public Label getLabel() {
		return label;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
