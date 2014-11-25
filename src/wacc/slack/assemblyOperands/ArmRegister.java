package wacc.slack.assemblyOperands;

public enum ArmRegister implements Register{
	r0, r1, r2, r3, r4, r5, r6, r7, r8, r9,
	r10, r11, r12, sp, lr, pc, CPSR, SPSR;

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getWeight() {
		return 1;//TODO:
	}
}

