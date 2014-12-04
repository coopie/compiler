package wacc.slack.assemblyOperands;

public class Address implements Operand {

	private final Register r;
	private Integer offset = null;
	private Register regOffset = null;
	
	public Address(Register r) {
		this.r = r;
		this.offset = 0;
	} 
	
	public Address(Register r, int offset) {
		this.r = r;
		this.offset = offset;
	}
	
	public Address(Register r, Register regOffset) {
		this.r = r;
		this.regOffset = regOffset;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public Register getRegister() {
		return r;
	}
	
	public Integer getOffset() {
		return offset;
	}
	
	public Register getRegOffset() {
		return regOffset;
	}
	@Override
	public String toString() {
		String s = "[" + r + " ,";
		if(offset != null) {
			s += offset;
		} else if(regOffset != null) {
			s += regOffset;
		}
		s += "]";
		return s;
	}

}
