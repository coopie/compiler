package wacc.slack.assemblyOperands;

public class RealRegister implements Operand {

	private final ArmRegister ar;
	
	public RealRegister(ArmRegister ar) {
		this.ar = ar;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public ArmRegister getAr() {
		return ar;
	}

}
