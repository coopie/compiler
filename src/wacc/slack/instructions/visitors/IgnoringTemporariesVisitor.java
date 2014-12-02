package wacc.slack.instructions.visitors;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.instructions.Label;

public class IgnoringTemporariesVisitor implements OperandVisitor<String> {

	private String immediateValuePrefix = "#";

	public void setImmediateValuePrefix(String prefix) {
		immediateValuePrefix = prefix;
	}

	@Override
	public String visit(ArmRegister realRegister) {
		setImmediateValuePrefix("#");
		return realRegister.name();
	}

	@Override
	public String visit(TemporaryRegister temporaryRegister) {
		setImmediateValuePrefix("#");
		return "R" + temporaryRegister.getN();
	}

	@Override
	public String visit(Label label) {
		setImmediateValuePrefix("#");
		return "=" + label.getName();
	}

	@Override
	public String visit(ImmediateValue immediateValue) {
		String result = immediateValue.getValue(immediateValuePrefix);
		// You only need to set it for things that aren't # and it reverts
		// back after you use it
		setImmediateValuePrefix("#");
		return result;
	}

	@Override
	public String visit(Address address) {
		setImmediateValuePrefix("#");
		if ((address.getOffset() == null || address.getOffset() == 0)
				&& (address.getRegOffset() == null)) {
			return "[" + address.getRegister().accept(this) + "]";
		} else if (address.getOffset() != null && address.getOffset() != 0
				&& address.getRegOffset() == null) {
			return "[" + address.getRegister().accept(this) + ", #"
					+ address.getOffset() + "]";
		} else if ((address.getOffset() == null || address.getOffset() == 0)
				&& (address.getRegOffset() != null)) {
			return "[" + address.getRegister().accept(this) + ", #"
					+ address.getRegOffset().accept(this) + "]";
		} else {
			// TODO: If we need this
			return null;
		}
	}

	@Override
	public String visit(NoOperand noOperand) {
		return "";
	}

}
