package wacc.slack.assemblyOperands;

import wacc.slack.instructions.Label;

public interface OperandVisitor<T> {

	// These are used to know what kind of instrution the operand is in. Useful
	// for immediateValues when you dont know if you want to use a # or a =
	void setImmediateValuePrefix(String prefix);

	T visit(ArmRegister realRegister);

	T visit(TemporaryRegister temporaryRegister);

	T visit(Label label);

	T visit(ImmediateValue immediateValue);

	T visit(Address address);

}
