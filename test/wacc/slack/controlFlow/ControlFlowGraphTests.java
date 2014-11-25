package wacc.slack.controlFlow;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;

public class ControlFlowGraphTests {
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private BranchInstruction branchStart = new BranchInstruction(Condition.LT,new Label("start"));
	
	Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
			branch,
			new Label("start"),
			mov,
			new Label("l1"),
			cmp,
			branchStart,
			branch
			));
	

	@Test
	public void canCreateCFGNodeWithPseudoInstruction() {
		CFGNode n = new CFGNode(mov);
		assertThat(n.getInstruction(),is(mov));
	}
	
	@Test
	public void canGetRegistersDefinedByInstruction() {
		CFGNode n = new CFGNode(mov);
		assertThat(n.getDefinitions(),hasItems((Register)ArmRegister.r0));
	}
	
	@Test
	public void canGetRegistersUsedByInstruction() {
		CFGNode n = new CFGNode(mov);
		assertThat(n.getUses(),hasItems((Register)ArmRegister.r1));
	}
	
	@Test
	public void canLinkAndTraverseCFGNodes() {
		CFGNode n1 = new CFGNode(mov);
		CFGNode n2 = new CFGNode(mov);
		CFGNode n3 = new CFGNode(mov);
		
		n1.setNext(n2);
		n1.setNext(n3);
		n2.setNext(n3);
		n3.setNext(n1);
		
		assertThat(n1.getNext(),hasItems(n2,n3));
		assertThat(n2.getNext(),hasItems(n3));
		assertThat(n3.getNext(),hasItems(n1));
	}
	
	@Test
	public void canAddNullAsNoNextNode() {
		CFGNode n = new CFGNode(mov);
		n.setNext(null);
		assertThat(n.getNext().size(), is(0));
	}
	
	@Test
	public void cFGGraphHasCorrectRoot() {
		CFGNode root = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(cmp,mov)));	
		assertThat(root.getInstruction(), is((PseudoInstruction)cmp));
	}
	
	@Test
	public void cFGGraphCanHaveOneNextNode() {
		CFGNode root = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(cmp,mov)));	
		assertThat(root.getNext().get(0).getInstruction(), is((PseudoInstruction)mov));
	}
	
	@Test
	public void cFGGraphCanIncludeLabels() {
		CFGNode root = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(branch,new Label("l1"),mov)));	
		assertThat(root.getNext().get(0).getInstruction(), is((PseudoInstruction)mov));
	}
	
	@Test
	public void labelsAreAddedToTheLabelLookup() {
		Map<Label,CFGNode> labelLookup = new HashMap<>();
		CFGNode.makeGraph(new LinkedList<PseudoInstruction>(Arrays.asList(branch,new Label("l1"),mov)),labelLookup);	
		assertThat(labelLookup.get(new Label ("l1")).getInstruction(), is(mov));
	}

	@Test
	public void branchInstructionHasOneNext() {
		Map<Label,CFGNode> labelLookup = new HashMap<>();
		CFGNode root = CFGNode.makeGraph(new LinkedList<PseudoInstruction>(Arrays.asList(new Label("l1"),mov,branch,cmp)),labelLookup);	
		assertThat(root.getNext().get(0).getNext().size(), is(1));
		assertThat(root.getNext().get(0).getNext().get(0).getInstruction(), is(mov));
	}

	@Test
	public void branchInstructionHasTwoNexts() {
		Map<Label,CFGNode> labelLookup = new HashMap<>();
		CFGNode root = CFGNode.makeGraph(new LinkedList<PseudoInstruction>(Arrays.asList(new Label("start"),mov,branchStart,cmp)),labelLookup);	
		assertThat(root.getNext().get(0).getNext().size(), is(2));
		assertThat(root.getNext().get(0).getNext().get(1).getInstruction(), is(mov));
		assertThat(root.getNext().get(0).getNext().get(0).getInstruction(), is(cmp));
	}

}
