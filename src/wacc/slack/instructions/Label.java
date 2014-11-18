package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Label  implements PseudoInstruction, Operand {

	private final String name;
	
	public Label(String name) {
		this.name = name;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.accept(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
