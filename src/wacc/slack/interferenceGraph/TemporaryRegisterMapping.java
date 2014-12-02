package wacc.slack.interferenceGraph;

import java.util.Map;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.TemporaryRegister;

public class TemporaryRegisterMapping {

	private final Map<TemporaryRegister, ArmRegister> resolvedRegisters;
	
	private final Map<TemporaryRegister, TemporaryRegister> spilledRegisters;

	public TemporaryRegisterMapping(
			Map<TemporaryRegister, ArmRegister> resolvedRegisters,
			Map<TemporaryRegister, TemporaryRegister> spilledRegisters) {
		super();
		this.resolvedRegisters = resolvedRegisters;
		this.spilledRegisters = spilledRegisters;
	}


	public Map<TemporaryRegister, TemporaryRegister> getSpilledRegisters() {
		return spilledRegisters;
	}


	public Map<TemporaryRegister, ArmRegister> getResolvedRegisters() {
		return resolvedRegisters;
	}
	
	@Override
	public String toString() {
		return resolvedRegisters + "\n" + spilledRegisters;
	}
	

	
}
