package wacc.slack.controlFlow;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.instructions.PseudoInstruction;

public class CFGNode {
	// following the structure from the lecture slides for registerAllocation
	
	private final PseudoInstruction ps;

	private List<CFGNode> defs = new LinkedList<CFGNode>();
	private List<CFGNode> uses = new LinkedList<CFGNode>();
	
	private List<CFGNode> next = new LinkedList<CFGNode>();
	

	public CFGNode(PseudoInstruction ps) {
		this.ps = ps;
	}
	
	
	
	

}
