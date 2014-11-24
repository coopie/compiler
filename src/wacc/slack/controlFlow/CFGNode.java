package wacc.slack.controlFlow;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.GetDefinedRegisters;
import wacc.slack.instructions.visitors.GetUsedRegisters;

public class CFGNode {
	// following the structure from the lecture slides for registerAllocation
	
	private final PseudoInstruction ps;

	private List<Register> defs = new LinkedList<>();
	private List<Register> uses = new LinkedList<>();
	
	private List<CFGNode> next = new LinkedList<CFGNode>();
	

	public CFGNode(PseudoInstruction ps) {
		this.ps = ps;
		defs = ps.accept(new GetDefinedRegisters());
		uses = ps.accept(new GetUsedRegisters());
	}


	public PseudoInstruction getInstruction() {
		return ps;
	}


	public Iterable<Register> getDefinitions() {
		return defs;
	}


	public Iterable<Register> getUses() {
		return uses;
	}


	public void setNext(CFGNode n2) {
		next.add(n2);
	}


	public Iterable<CFGNode> getNext() {
		return next;
	}	

}
