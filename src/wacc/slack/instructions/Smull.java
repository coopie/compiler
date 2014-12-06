package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Smull implements PseudoInstruction {

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}
	
	private final Operand rdLo;
	private final Operand rdHi;
	private final Operand rm;
	private final Operand rs;
	
	public Smull(Operand rdLo, Operand rdHi, Operand rm, Operand rs) {
		super();
		this.rdLo = rdLo;
		this.rdHi = rdHi;
		this.rm = rm;
		this.rs = rs;
		if(rdHi != rm) {
			throw new RuntimeException("Smull can only use 3 registers in this compiler, deal with it, complex register allocator will get complex if used");
		}
	}

	public Operand getRdLo() {
		return rdLo;
	}

	public Operand getRdHi() {
		return rdHi;
	}

	public Operand getRm() {
		return rm;
	}

	public Operand getRs() {
		return rs;
	}


	
}
