package wacc.slack.assemblyOperands;

public class RealRegister implements Register {

	private final ArmRegister ar;
	
	public RealRegister(ArmRegister ar) {
		this.ar = ar;
	}

}
