package wacc.slack.instructions;

public class BranchInstruction {
	
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

}
