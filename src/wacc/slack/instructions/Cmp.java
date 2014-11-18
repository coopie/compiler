package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.visitors.InstructionVistor;

public class Cmp implements PseudoInstruction {
	
	private Operand op1;
	private Operand op2;
	
	public Cmp(Operand op1, Operand op2) {
		this.setOp1(op1);
		this.setOp2(op2);
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}
	
	public Operand getOp1() {
		return op1;
	}

	public void setOp1(Operand op1) {
		this.op1 = op1;
	}

	public Operand getOp2() {
		return op2;
	}

	public void setOp2(Operand op2) {
		this.op2 = op2;
	}
	
}
