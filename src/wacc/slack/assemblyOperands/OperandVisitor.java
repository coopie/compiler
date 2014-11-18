package wacc.slack.assemblyOperands;

import wacc.slack.instructions.Label;

public interface OperandVisitor<T> {

	T visit(RealRegister realRegister);

	T visit(TemporaryRegister temporaryRegister);

	T visit(Label label);

}
