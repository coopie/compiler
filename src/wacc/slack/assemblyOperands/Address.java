package wacc.slack.assemblyOperands;

public class Address implements Operand {

	private final Register r;
	private final int offset;
	
	public Address(Register r, int offset) {
		this.r = r;
		this.offset = offset;
	}
	
	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public Register getRegister() {
		return r;
	}
	
	public int getOffset() {
		return offset;
	}

}
