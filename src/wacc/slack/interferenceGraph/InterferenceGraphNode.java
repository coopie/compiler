package wacc.slack.interferenceGraph;

import java.util.HashMap;
import java.util.Map;

import wacc.slack.assemblyOperands.Register;

class InterferenceGraphNode {

	private int colour = -1;
	
	private final int weight;
	private final Register reg;
	
	private static Map<Register, InterferenceGraphNode> registersMapped = new HashMap<>();
	
	
	public static InterferenceGraphNode getInterferenceGraphNodeForRegister(Register reg) {
		if(registersMapped.containsKey(reg)) {
			return registersMapped.get(reg);
		}
		InterferenceGraphNode n = new InterferenceGraphNode(reg);
		registersMapped.put(reg, n);
		return n;
	}
	
	//this gurantess there is only one interferencegraphNode for each register,
	//so for example keys in map are the same objects as the values
	// use the above static method
	private InterferenceGraphNode(Register reg) {
		this.reg = reg;
		this.weight = reg.getWeight();
	}

	
	public void colour(int c) {
		colour = c;
	}
	
	public boolean isColoured() {
		return colour != -1;
	}
	
	public int getColour() {
		return colour;
	}


	public int getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		//commenting this out, becuase overriding equals and feel like it is unsafe to do it that way
		/*if (reg instanceof ArmRegister) {
			return -1 * ((ArmRegister)reg).ordinal();
			
		} else {
			return ((TemporaryRegister)reg).getN();
		}*/
		return reg.hashCode();
		
	}
	
	@Override
	public boolean equals(Object o) {
		return reg.equals(o);
	}
}
