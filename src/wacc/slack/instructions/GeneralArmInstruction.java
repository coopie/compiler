package wacc.slack.instructions;

import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.Operand;

abstract class GeneralArmInstruction {
	
	protected final Operand source, source2;
	protected final Operand dest;
	
	protected Condition cond = Condition.AL;
	
	public GeneralArmInstruction(Operand dest, Operand source) {
		this.source = source;
		this.source2 = new NoOperand();
		this.dest = dest;
	}
	
	public GeneralArmInstruction(Operand dest, Operand source, Condition cond) {
		this.source = source;
		this.source2 = new NoOperand();
		this.dest = dest;
		this.cond = cond;
	}
	
	public GeneralArmInstruction(Operand dest, Operand source, Operand source2) {
		this.source = source;
		this.source2 = source2;
		this.dest = dest;
	}
	
	public GeneralArmInstruction(Operand dest, Operand source,
			Operand source2, Condition cond) {
		this.source = source;
		this.source2 = source2;
		this.dest = dest;	
		this.cond = cond;
	}

	public Operand getSource() {
		return source;
	}
	
	//TODO;: make this protected and override it where needed
	protected Operand getSource2() {
		return source2;
	}
	
	public Operand getDest() {
		return dest;
	}

	public Condition getCond() {
		return cond;
	}
	
}
