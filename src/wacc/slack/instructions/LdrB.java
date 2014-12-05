package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class LdrB extends GeneralArmInstruction implements PseudoInstruction {
	
	public LdrB(Operand dest, Operand source) {
		super(dest, source);
	}

	public LdrB(Operand dest, Operand source, Condition cond) {
		super(dest, source, cond);
	}
	
	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "LDRB " + dest + " " + source;
	}


}
