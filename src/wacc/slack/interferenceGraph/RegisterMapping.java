package wacc.slack.interferenceGraph;

import wacc.slack.assemblyOperands.Register;

public interface RegisterMapping {

	public abstract Register getRegisterSwap(Register r);

}