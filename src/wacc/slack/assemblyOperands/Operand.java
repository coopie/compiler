package wacc.slack.assemblyOperands;

public interface Operand {
	<T> T accept(OperandVisitor<T> visitor);
}
