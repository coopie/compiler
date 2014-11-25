package wacc.slack.controlFlow;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
	public void canMakeGraph() {
		Map<CFGNode,Set<CFGNode>> graph = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(cmp,mov)));	
		assertThat(graph.keySet().size(), is(2));
	
	}
	
	@Test
	public void cFGGraphCanHaveOneNextNode() {
		Map<CFGNode,Set<CFGNode>> graph = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(cmp,mov)));	
		
		assertThat(graph.keySet().size(),is(2));
		for(CFGNode n : graph.keySet()) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.get(n).size(),is(1));
				assertThat(graph.get(n).iterator().next().getInstruction(), is(mov));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.get(n).size(),is(0));
			} else {
				assertFalse(true);
			}
		}
	}
	
	@Test
	public void cFGGraphCanIncludeLabels() {
		Map<CFGNode,Set<CFGNode>> graph = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(branch,new Label("l1"),mov)));	
		assertThat(graph.keySet().size(),is(2));
	}

	@Test
	public void branchInstructionHasOneNext() {
		Map<CFGNode,Set<CFGNode>> graph = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(new Label("l1"),mov,branch,cmp)));	

		assertThat(graph.keySet().size(),is(3));
		for(CFGNode n : graph.keySet()) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.get(n).size(),is(0));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.get(n).size(),is(1));
			} else if (n.getInstruction() == branch) {
				assertThat(graph.get(n).size(),is(1));
				assertThat(graph.get(n).iterator().next().getInstruction(), is(mov));
			} else {
				assertFalse(true);
			}
		}
	}

	@Test
	public void branchInstructionHasTwoNexts() {
		Map<CFGNode,Set<CFGNode>> graph = CFGNode.makeGraph(new LinkedList<>(Arrays.asList(new Label("start"),mov,branchStart,cmp)));	

		assertThat(graph.keySet().size(),is(3));
		for(CFGNode n : graph.keySet()) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.get(n).size(),is(0));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.get(n).size(),is(1));
			} else if (n.getInstruction() == branchStart) {
				assertThat(graph.get(n).size(),is(2));
			} else {
				assertFalse(true);
			}
		}
	}
}
