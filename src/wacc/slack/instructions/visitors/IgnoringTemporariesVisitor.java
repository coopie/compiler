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
		return realRegister.name();
	}

	@Override
	public String visit(TemporaryRegister temporaryRegister) {
		return "R" + temporaryRegister.getN();
	}

	@Override
	public String visit(Label label) {
		return "=" + label.getName();
	}

	@Override
	public String visit(ImmediateValue immediateValue) {
		String result = immediateValue.getValue(immediateValuePrefix);
		return result;
	}

	@Override
	public String visit(Address address) {
		if ((address.getOffset() == null || address.getOffset() == 0)
				&& (address.getRegOffset() == null)) {
			return "[" + address.getRegister().accept(this) + "]";
		} else if (address.getOffset() != null && address.getOffset() != 0
				&& address.getRegOffset() == null) {
			return "[" + address.getRegister().accept(this) + ", #"
					+ address.getOffset() + "]";
		} else if ((address.getOffset() == null || address.getOffset() == 0)
				&& (address.getRegOffset() != null)) {
			return "[" + address.getRegister().accept(this) + ", "
					+ address.getRegOffset().accept(this) + "]";
		} else {
			throw new RuntimeException("Trying to visit address in ignoring temporaries visitor that isn't supported yet.");
		}
	}

	@Override
	public String visit(NoOperand noOperand) {
		return "";
	}

}
