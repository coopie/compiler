package wacc.slack.assemblyOperands;

/*
 * Positive temporary registers represent the ones to be spilled or used in grpah colouring
 * Negative ones represent stack variables before the frame pointer
 */
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
	
	@Override
	public String toString() {
		return "t" + n;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof TemporaryRegister) {
			return n == ((TemporaryRegister) o).getN();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return n;
	}
	
	
	
}
