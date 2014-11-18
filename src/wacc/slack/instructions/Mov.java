package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Mov implements PseudoInstruction {

	private Operand dest;
	private Operand source;
	
	public Mov(Operand dest, Operand source) {
		this.setDest(dest);
		this.setSource(source);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public Operand getDest() {
		return dest;
	}

	public void setDest(Operand dest) {
		this.dest = dest;
	}

	public Operand getSource() {
		return source;
	}

	public void setSource(Operand source) {
		this.source = source;
	}

}
