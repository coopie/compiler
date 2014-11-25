package wacc.slack.controlFlow;

import java.util.HashSet;
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
	private final Set<CFGNode> instructions = new HashSet<>();

	public LabelBabySitter(Map<Label, CFGNode> labelLookup) {
		this.labelLookup = labelLookup;
	}

	public boolean allInstructionsHappy() {
		CFGNode next;
		for(CFGNode n : instructions) {
			next = labelLookup.get(n.getInstruction().accept(labelVisitor));
			if(next != null) {
				n.setNext(next);
				instructions.remove(n);
			}
		}
		return instructions.size() == 0;
	}

	public void add(CFGNode node) {
		if(node.getInstruction().accept(boolvisitor)) {
			instructions.add(node);
		}
	}

}
