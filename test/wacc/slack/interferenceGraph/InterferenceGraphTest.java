package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;

public class InterferenceGraphTest {
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private PseudoInstruction add = new Add(ArmRegister.r2, ArmRegister.r1, ArmRegister.r3);
	private BranchInstruction branchStart = new BranchInstruction(Condition.LT,new Label("start"));
	
	
	
	public InterferenceGraph complexGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				branch,
				new Label("start"),
				mov,
				new Label("l1"),
				cmp,
				branchStart,
				branch
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
	
	public InterferenceGraph simpleGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				mov, cmp, add
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
	

	@Test
	public void nodeDoesNotNeighbourtself() {
		InterferenceGraph ig = complexGraph();
		for(InterferenceGraphNode n : ig) {
			if(ig.getAdjecent(n).contains(n)) {
				assertTrue(false);
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void canIterateThroughAllNodes() {
		int count = 0;
		InterferenceGraph ig = complexGraph();
		for(InterferenceGraphNode n : ig) {
			count++;
		}
		assertThat(count, is(ig.nodeSet().size()));
	}
	
	@Test
	public void canCleanItself() {
		InterferenceGraph ig = complexGraph();
		for(InterferenceGraphNode n : ig) {
			n.colour(5);
			n.clean();
		}
		boolean cleaningWorked = true;
		for(InterferenceGraphNode n : ig) {
			cleaningWorked &= !(n.isColoured());
		}
		assertThat(cleaningWorked, is(true));
	}
	
	@Test
	public void isNotConstrainedWhenNodeHasLessThanKNeighbours() {
		InterferenceGraph ig = simpleGraph();
		boolean constrainedQueryWorking = true;
		for(InterferenceGraphNode n : ig) {
			constrainedQueryWorking = 
					ig.isConstrained(n, 1) == ig.getAdjecent(n).size() >= 1;
		}
		assertThat(constrainedQueryWorking, is(true));
	}
	
	@Test
	public void containsAllRegistersInCode() {
		InterferenceGraph ig  = simpleGraph();
		Map<InterferenceGraphNode, Set<InterferenceGraphNode>> adjacencyList =
				ig.getAdjecencyList();
		
		List<Register> regsInGraph = new LinkedList<>();
		for (InterferenceGraphNode n : adjacencyList.keySet()) {
			regsInGraph.add(n.getRegister());
		}
		assertThat(regsInGraph, hasItems((Register)ArmRegister.r0,
				ArmRegister.r1,
				ArmRegister.r2,
				ArmRegister.r3));
	}
}
