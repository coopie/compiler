package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Pop implements PseudoInstruction {

	private final Operand operand;
	
	public Pop(Operand operand) {
		this.operand = operand;
	}

	public Operand getOperand() {
		return operand;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
