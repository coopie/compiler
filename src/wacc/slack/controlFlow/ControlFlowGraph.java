package wacc.slack.controlFlow;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.AbstractInstructionVisitor;

public class ControlFlowGraph extends AbstractGraph<CFGNode> {

	private final AbstractInstructionVisitor<Label> labelVisitor = new AbstractInstructionVisitor<Label>(new Callable<Label>(){
		@Override
		public Label call() throws Exception {
			return null;
		}}){
			@Override
			public Label visit(Label l) {
				return l;
			}
	};
	
	private final AbstractInstructionVisitor<Boolean> isNextInstructionExecuted = new AbstractInstructionVisitor<Boolean>(new Callable<Boolean>(){
		@Override
		public Boolean call() throws Exception {
			return true;
		}}){
			@Override
			public Boolean visit(BranchInstruction b) {
				return b.getCond() != Condition.AL;
			}
	};
	
	private Set<Register> allRegisters;
	
	public ControlFlowGraph(Deque<PseudoInstruction> code) {
	//	Map<CFGNode,Set<CFGNode>> graph = new HashMap<>();
		super();
		Map<Label,CFGNode> labelLookUp = new HashMap<>();


		Iterator<PseudoInstruction> i = code.descendingIterator();
		LabelBabySitter sitter = new LabelBabySitter(labelLookUp);
		
		PseudoInstruction currentInstruction;
		CFGNode prevNode  = null;
		CFGNode currentNode = null;
		Label l = null;
		Set<CFGNode> nexts;
		while(i.hasNext()) {
			currentInstruction = i.next();
			//if the current instruction is label we add it to the look up and 
			//carry on as if there were no pseudo instruction
			l = currentInstruction.accept(labelVisitor);
			if(l != null) {
				labelLookUp.put(l,prevNode);
				continue;
			}
			currentNode = new CFGNode(currentInstruction);
			nexts = new HashSet<>();
			sitter.add(currentNode, nexts);
			if(prevNode != null && currentInstruction.accept(isNextInstructionExecuted)) {
				nexts.add(prevNode);
			}
			this.putNode(currentNode, nexts);
			prevNode = currentNode;
		}
		
		if(!sitter.allInstructionsHappy()) {
			throw new RuntimeException("undefined label found");
		}
	}

	public Map<CFGNode,Set<Register>> getLiveOut() {
		Map<CFGNode,Set<Register>> liveOut = new HashMap<>();
		Map<CFGNode,Set<Register>> liveIn = new HashMap<>();
		
		Map<CFGNode,Set<Register>> liveOutPrevSize = new HashMap<>();
		Map<CFGNode,Set<Register>> liveInPrevSize = new HashMap<>();

		for(CFGNode n : this) {
			Set<Register> set1 = new HashSet<Register>();
			Set<Register> set2 = new HashSet<Register>();
			liveIn.put(n, set1);
			liveOut.put(n,set2);
			liveInPrevSize.put(n, set1);
			liveOutPrevSize.put(n, set2);
		}
		
		Set<Register> liveInN; 
		Set<Register> liveOutN; 
		Set<Register> tmp;
		Set<CFGNode> succN;
		allRegisters = new HashSet<Register>();
		do {
			for(CFGNode n : this) {
				//LiveIn(n) = uses(n) U (LiveOut(n) � defs(n))
				liveInN = liveIn.get(n);
				liveOutN = liveOut.get(n);
				
				//LiveIn(n) U uses(n)
				liveInN.addAll(n.uses);
				//LiveIn(n) U (LiveOut(n) � defs(n))
				tmp = new HashSet<Register>(liveOutN);
				tmp.removeAll(n.defs);
				liveInN.addAll(tmp);
				
				//	LiveOut(n) = Us elem succ(n) LiveIn(s); 
				succN = this.getAdjecent(n);
				for(CFGNode sn : succN) {
					liveOutN.addAll(liveIn.get(sn));
				}
				allRegisters.addAll(liveOutN);
				
				
			}
		}while(isChanged(liveIn,liveInPrevSize) &&  isChanged(liveIn,liveInPrevSize));
		System.out.println(printGraph(liveIn));
		System.out.println(printGraph(liveOut));
		
		return liveOut;
	}
	
	public Set<Register> getAllRegs() {
		return allRegisters;
	}
	
	private  <T,R> boolean isChanged(Map<T,Set<R>> graph, Map<T,Set<R>> previousSets) {
		assert graph.keySet().equals(previousSets.keySet()): "must call isChanged with identical key sets";
		
		boolean changed = false;
		for(T n : graph.keySet()) {
			if(graph.get(n).equals(previousSets.get(n))) {
				previousSets.put(n, graph.get(n));
			} else {
				changed = true;
			}
			
		}
		
		return changed;
	}
}
