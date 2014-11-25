package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Label implements PseudoInstruction, Operand {
	
	private final String name;
	
	public Label(String name) {
		this.name = name;
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Label) {
			return name.equals(((Label)o).getName());
		}
		return false;
	}
	
	@Override 
	public int hashCode() {
		return name.hashCode();
	}

}
