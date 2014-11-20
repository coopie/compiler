package wacc.slack.instructions;

import wacc.slack.assemblyOperands.Operand;

abstract class GeneralArmInstruction {
	
	protected final Operand source;
	protected final Operand dest;
	
	protected Condition cond = Condition.AL;
	
	public GeneralArmInstruction(Operand dest, Operand source) {
		this.source = source;
		this.dest = dest;
	}
	
	public GeneralArmInstruction(Operand dest, Operand source, Condition cond) {
		this.source = source;
		this.dest = dest;
		this.cond = cond;
	}
	
	public Operand getSource() {
		return source;
	}
	
	public Operand getDest() {
		return dest;
	}

}
