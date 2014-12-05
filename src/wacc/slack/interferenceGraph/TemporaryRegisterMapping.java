package wacc.slack.interferenceGraph;

import java.util.Map;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;

public class TemporaryRegisterMapping implements RegisterMapping {

	private final Map<TemporaryRegister, ArmRegister> resolvedRegisters;
	
	private final Map<TemporaryRegister, TemporaryRegister> spilledRegisters;

	public TemporaryRegisterMapping(
			Map<TemporaryRegister, ArmRegister> resolvedRegisters,
			Map<TemporaryRegister, TemporaryRegister> spilledRegisters) {
		super();
		this.resolvedRegisters = resolvedRegisters;
		this.spilledRegisters = spilledRegisters;
	}


	Map<TemporaryRegister, TemporaryRegister> getSpilledRegisters() {
		return spilledRegisters;
	}


	Map<TemporaryRegister, ArmRegister> getResolvedRegisters() {
		return resolvedRegisters;
	}
	
	/* (non-Javadoc)
	 * @see wacc.slack.interferenceGraph.RegisterMapping#getRegisterSwap(wacc.slack.assemblyOperands.Register)
	 */
	@Override
	public Register getRegisterSwap(Register r) {
		if(resolvedRegisters.containsKey(r)) {
			return resolvedRegisters.get(r);
		} else if(spilledRegisters.containsKey(r)) {
			return spilledRegisters.get(r);
		} else {
			throw new RuntimeException("Register " + r + " not in mapping");
		}
	}
	
	
	@Override
	public String toString() {
		return resolvedRegisters + "\n" + spilledRegisters;
	}
	

	
}
