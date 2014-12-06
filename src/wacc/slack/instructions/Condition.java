package wacc.slack.instructions;

// if there is a condition that you would like to have in this class
// add it!

public enum Condition {
	EQ, NE, GT, GE, LT, LE, AL, CS, VS;

	@Override
	public String toString() {
		if(this == AL) {
			return "";
		} else {
			return this.name();
		}
	}
}
