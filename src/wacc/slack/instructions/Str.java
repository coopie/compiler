package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Str extends GeneralArmInstruction implements PseudoInstruction {

	public final int offset;

	// IMPORTANT:
	// THE ORDER OF SOURCE AND DEST HAVE CHANGED SO IT IS MORE SIMILAR TO THE
	// ARM INSTRUCTION

	public Str(Operand source, Operand dest, int offset) {
		super(dest, source);
		this.offset = offset;
	}
	
	public Str(Operand source, Operand dest) {
		super(dest, source);
		this.offset = 0;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public int getOffset() {
		return offset;
	}
}
