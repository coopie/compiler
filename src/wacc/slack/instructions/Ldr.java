package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Ldr implements PseudoInstruction {
	
	private final Operand source;
	private final Operand dest;

	public Ldr(Operand dest, Operand source) {
		this.dest = dest;
		this.source = source;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public Operand getSource() {
		return source;
	}

	public Operand getDest() {
		return dest;
	}

}
