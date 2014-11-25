package wacc.slack.assemblyOperands;

public class TemporaryRegister implements Register {
	
	private final int n;
	private int weight = 1;

	// use the generator to make these!!
	public TemporaryRegister(int n, int weight) {
		this.n = n;
		this.weight = weight;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public int getN() {
		return n;
	}
	
	@Override
	public int getWeight() {
		return weight;
	}
	
	
}
