package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Sub extends GeneralArmInstruction implements PseudoInstruction {

	public Sub(Operand dest, Operand source, Operand source2) {
		super(dest, source, source2);
	}
	
	public Sub(Operand dest, Operand source) {
		super(dest, source);
	}

	public Sub(Operand dest, Operand source, Operand source2, Condition cond) {
		super(dest, source, source2, cond);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}
	
	public Operand getSource2() {
		return super.getSource2();
	}
}
 