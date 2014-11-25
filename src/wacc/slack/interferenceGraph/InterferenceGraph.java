package wacc.slack.interferenceGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.controlFlow.AbstractGraph;
import wacc.slack.controlFlow.CFGNode;

public class InterferenceGraph extends AbstractGraph<InterferenceGraphNode> {

	
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
					this.putNode(InterferenceGraphNode.getInterferenceGraphNodeForRegister(t), convertRegisters(liveOutId));
				}
			}
		}
	}
	
	private Set<InterferenceGraphNode> convertRegisters(Set<Register> regs) {
		Set<InterferenceGraphNode> n = new HashSet<>();
		for(Register r : regs) {
			n.add(InterferenceGraphNode.getInterferenceGraphNodeForRegister(r));
		}
		return n;
	}
	
	public void clean() {
		//TODO:
	}
}
