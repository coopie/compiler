package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class And implements PseudoInstruction{

	private final Operand source1, source2;
	private final Operand dest;
	
	public And(Operand dest, Operand source1, Operand source2) {
		this.source1 = source1;
		this.source2 = source2;
		this.dest = dest;
	}
	
	
	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}


	public Operand getSource1() {
		return source1;
	}


	public Operand getSource2() {
		return source2;
	}


	public Operand getDest() {
		return dest;
	}
	
}
 