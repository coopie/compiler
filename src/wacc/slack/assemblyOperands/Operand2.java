package wacc.slack.assemblyOperands;

public class Operand2 implements Operand {

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	private final Register r;
	private final String barrelShifterArgument;
	
	public Operand2(Register r, String barrelShifterArgument) {
		super();
		this.r = r;
		this.barrelShifterArgument = barrelShifterArgument;
	}

	public Register getR() {
		return r;
	}

	public String getBarrelShifterArgument() {
		return barrelShifterArgument;
	}
	

}
