package wacc.slack.interferenceGraph;

import java.util.HashMap;
import java.util.Map;

import wacc.slack.assemblyOperands.Register;

//TDD
class InterferenceGraphNode implements Comparable<InterferenceGraphNode>{

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
	
	//this guarantees there is only one interferencegraphNode for each register,
	//so for example keys in map are the same objects as the values
	// use the above static method
	InterferenceGraphNode(Register reg) {
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
	
	public void clean() {
		colour = -1;
	}

	public int getWeight() {
		return weight;
	}
	
	public Register getRegister() {
		return reg;
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

	@Override
	public int compareTo(InterferenceGraphNode n) {
		return weight - n.getWeight();
	}
	
	@Override
	public String toString() {
		return "(" + reg + ", " + weight + ", *" + colour +  "*)";
	}
}
