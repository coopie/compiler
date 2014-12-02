package wacc.slack.assemblyOperands;

public class NoOperand implements Operand {

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
