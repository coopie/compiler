package wacc.slack.interferenceGraph;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;

public class InterferenceGraphNode {

	private int colour = -1;
	
	private final int weight;
	private final Register reg;
	
	public InterferenceGraphNode(Register reg, int weight) {
		this.reg = reg;
		this.weight = weight;
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
		if (reg instanceof ArmRegister) {
			return -1 * ((ArmRegister)reg).ordinal();
			
		} else {
			return ((TemporaryRegister)reg).getN();
		}
		
	}
}
