package wacc.slack.controlFlow;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.AbstractInstructionVisitor;
import wacc.slack.instructions.visitors.GetDefinedRegisters;
import wacc.slack.instructions.visitors.GetUsedRegisters;
//TDD class
public class CFGNode {
	// following the structure from the lecture slides for registerAllocation
	
	private final PseudoInstruction ps;

	private List<Register> defs = new LinkedList<>();
	private List<Register> uses = new LinkedList<>();
	
	private List<CFGNode> next = new LinkedList<CFGNode>(); 
	
	private static final AbstractInstructionVisitor<Label> labelVisitor = new AbstractInstructionVisitor<Label>(new Callable<Label>(){
		@Override
		public Label call() throws Exception {
			return null;
		}}){
			@Override
			public Label visit(Label l) {
				return l;
			}
	};
	
	private static final AbstractInstructionVisitor<Boolean> isNextInstructionExecuted = new AbstractInstructionVisitor<Boolean>(new Callable<Boolean>(){
		@Override
		public Boolean call() throws Exception {
			return true;
		}}){
			@Override
			public Boolean visit(BranchInstruction b) {
				return b.getCond() != Condition.AL;
			}
	};
	

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
		return makeGraph(code, new HashMap<Label,CFGNode>());
	}
	
	private static Label isLabel(PseudoInstruction instr) {
		return instr.accept(labelVisitor);
	}
	static CFGNode makeGraph(Deque<PseudoInstruction> code, Map<Label,CFGNode> labelLookUp) {
		Iterator<PseudoInstruction> i = code.descendingIterator();
		LabelBabySitter sitter = new LabelBabySitter(labelLookUp);
		
		PseudoInstruction currentInstruction;
		CFGNode prevNode  = null;
		CFGNode currentNode = null;
		Label l = null;
		//TODO: a lot
		while(i.hasNext()) {
			currentInstruction = i.next();
			//if the current instruction is label we add it to the look up and 
			//carry on as if there were no pseudo instruction
			l = isLabel(currentInstruction);
			if(currentInstruction instanceof Label) {
				labelLookUp.put(l,prevNode);
				continue;
			}
			currentNode = new CFGNode(currentInstruction);
			sitter.add(currentNode);
			if(currentInstruction.accept(isNextInstructionExecuted)) {
				currentNode.setNext(prevNode);
			}
			prevNode = currentNode;
		}
		
		if(!sitter.allInstructionsHappy()) {
			throw new RuntimeException("undefined label found");
		}
		
		return currentNode;
	}

}
