package wacc.slack.controlFlow;

import java.util.Map;
import java.util.Set;

import wacc.slack.assemblyOperands.Register;

public class InterferenceGraph extends AbstractGraph<Register> {

	public InterferenceGraph(Map<CFGNode,Set<Register>> liveOut, Set<Register> allRegisters) {
		Set<Register> liveOutId;
		// For each temporary t 
		for(Register t : allRegisters) {
			// For each node id 
			for(CFGNode id: liveOut.keySet()) {
				liveOutId = liveOut.get(id); 
				// If t is in liveOut(id)
				if(liveOutId.contains(t)) {
					//Then interferes(t) includes liveOut(id) 
					this.putNode(t, liveOutId);
				}
			}
		}
	}
}
