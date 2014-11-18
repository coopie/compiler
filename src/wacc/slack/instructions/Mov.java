package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Mov implements PseudoInstruction {

	private Register dest;
	private Register source;
	
	public Mov(Register dest, Register source) {
		this.setDest(dest);
		this.setSource(source);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.accept(this);
	}

	public Register getDest() {
		return dest;
	}

	public void setDest(Register dest) {
		this.dest = dest;
	}

	public Register getSource() {
		return source;
	}

	public void setSource(Register source) {
		this.source = source;
	}

}
