package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Push implements PseudoInstruction {

	private final Operand operand;
	
	public Push(Operand operand) {
		this.operand = operand;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public Operand getOperand() {
		return operand;
	}

}
