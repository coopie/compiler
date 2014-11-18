package wacc.slack.assemblyOperands;

public class TemporaryRegister implements Operand {
	
	private final int n;

	public TemporaryRegister(int n) {
		this.n = n;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	

}
