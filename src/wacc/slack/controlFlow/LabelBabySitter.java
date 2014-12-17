package wacc.slack.controlFlow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.visitors.AbstractInstructionVisitor;
/**
 * collects all the labels and instructions that reference the labels, and sets nexts accordingly
 */
class LabelBabySitter {

	private final class FalseCallable implements Callable<Boolean> {
		@Override
		public Boolean call() throws Exception {
			return false;
		}
	}

	private final AbstractInstructionVisitor<Boolean> boolvisitor = new AbstractInstructionVisitor<Boolean>(new FalseCallable()){
			@Override
			public Boolean visit(BranchInstruction b) {
				return true;
			}
	};
	
	private final AbstractInstructionVisitor<Label> labelVisitor = new AbstractInstructionVisitor<Label>(new Callable<Label>(){
		@Override
		public Label call() throws Exception {
			return null;
		}}){
			@Override
			public Label visit(BranchInstruction b) {
				return b.getLabel();
			}
	};
	
	private final Map<Label, CFGNode> labelLookup;
	private final Map<CFGNode,Set<CFGNode>> instructions = new HashMap<>();

	public LabelBabySitter(Map<Label, CFGNode> labelLookup) {
		this.labelLookup = labelLookup;
	}

	public boolean allInstructionsHappy() {
		CFGNode next;
		int count = 0;
		for(CFGNode n : instructions.keySet()) {
			next = labelLookup.get(n.getInstruction().accept(labelVisitor));
			if(next != null) {
				instructions.get(n).add(next);
				count++;
			} else {
//				System.out.println("n");
			}
		}
		return instructions.size() == count;
	}

	public void add(CFGNode node, Set<CFGNode> nexts) {
		if(node.getInstruction().accept(boolvisitor)) {
			instructions.put(node,nexts);
		}
	}

}
