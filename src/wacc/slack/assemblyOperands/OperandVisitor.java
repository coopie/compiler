package wacc.slack.assemblyOperands;

import wacc.slack.instructions.Label;

public interface OperandVisitor<T> {

	T visit(ArmRegister realRegister);

	T visit(TemporaryRegister temporaryRegister);

	T visit(Label label);

	T visit(ImmediateValue immediateValue);

	T visit(Address address);

	T visit(NoOperand noOperand);

	T visit(Operand2 operand2);

}
