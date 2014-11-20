package wacc.slack.assemblyOperands;

public class ImmediateValue implements Operand {

	private final String value;
	
	public ImmediateValue(String value) {
		this.value = "=" + value;
	}
	
	public ImmediateValue(int n) {
		this.value = "#" + n;
	}

	public String getValue() {
		return value;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
