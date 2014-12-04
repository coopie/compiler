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

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;

public class InterferenceGraphTest {
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private PseudoInstruction add = new Add(ArmRegister.r2, ArmRegister.r1, ArmRegister.r3);
	private PseudoInstruction str = new Str(new Address(ArmRegister.r1, 0), ArmRegister.r2);
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
				mov, cmp, add, str
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
	
	private TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
	
	TemporaryRegister t1 = trg.generate(1);
	TemporaryRegister t2 = trg.generate(1);
	TemporaryRegister t3 = trg.generate(1);
	TemporaryRegister t4 = trg.generate(1);
	TemporaryRegister t5 = trg.generate(1);
	TemporaryRegister t6 = trg.generate(1);
	TemporaryRegister t7 = trg.generate(1);
	TemporaryRegister t8 = trg.generate(1);
	TemporaryRegister t9 = trg.generate(1);
	TemporaryRegister t10 = trg.generate(1);
	
	private PseudoInstruction large1 = new Mov(t1, t2);
	private PseudoInstruction large2 = new Mov(t3, t2);
	private PseudoInstruction large3 = new Add(t1, t2, t3);
	private PseudoInstruction large4 = new And(t2, t1, t6);
	private PseudoInstruction large5 = new Ldr(new Address(t1, 2), t2);
	private PseudoInstruction large6 = new Mul(t1, t2, t6);
	private PseudoInstruction large7 = new Orr(t10, t7, t1);
	private PseudoInstruction large8 = new Sub(t10, t2, t6);
	private PseudoInstruction large9 = new Push(t2);
	private PseudoInstruction large10 = new Sub(t10, t2, t6);
	private PseudoInstruction large11 = new Label("blah");

	private PseudoInstruction largeEnd = new Str(new Address(t2, 3), t10);

	private InterferenceGraph bigTemporaryGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				large1,
				large2,
				large3,
				large4,
				large5,
				large6,
				large7,
				large8,
				large9,
				large10,
				large11,
				largeEnd
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
	
	@Test
	public void containsAllRegistersInCodeTest2() {
		InterferenceGraph ig  = bigTemporaryGraph();
		Map<InterferenceGraphNode, Set<InterferenceGraphNode>> adjacencyList =
				ig.getAdjecencyList();
		
		List<Register> regsInGraph = new LinkedList<>();
		for (InterferenceGraphNode n : adjacencyList.keySet()) {
			regsInGraph.add(n.getRegister());
		}
		
		assertThat(regsInGraph, hasItems((Register)t1, t2, t3, t6, t7, t10));
	}
	
	
}
