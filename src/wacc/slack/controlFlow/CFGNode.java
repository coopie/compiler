package wacc.slack.controlFlow;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.GetDefinedRegisters;
import wacc.slack.instructions.visitors.GetUsedRegisters;
//TDD class
public class CFGNode {
	// following the structure from the lecture slides for registerAllocation
	
	private final PseudoInstruction ps;

	private List<Register> defs = new LinkedList<>();
	private List<Register> uses = new LinkedList<>();
	
	private List<CFGNode> next = new LinkedList<CFGNode>();
	

	CFGNode(PseudoInstruction ps) {
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
		if(n2 != null)
			next.add(n2);
	}


	public List<CFGNode> getNext() {
		return next;
	}	
	
	public static CFGNode makeGraph(Deque<PseudoInstruction> code) {
		Iterator<PseudoInstruction> i = code.descendingIterator();
		
		PseudoInstruction currentInstruction;
		CFGNode prevNode  = null;
		CFGNode currentNode = null;
		
		//TODO: a lot
		while(i.hasNext()) {
			currentInstruction = i.next();
			currentNode = new CFGNode(currentInstruction);
			currentNode.setNext(prevNode);
			prevNode = currentNode;
		}
		
		return currentNode;
	}

}
