package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Sub;

public class InterferenceGraphColourerTest {

	
	@Test
	public void graphIsColouredSimple() {
		InterferenceGraphColourer colourer = colourerOfSimpleRealGraph();
		colourer.colour(10, 0);
		assertEquals("",colourer.toString());
	}
	
	@Test
	public void graphIsColouredSimpleUsingTemporaryRegisters() {
		InterferenceGraphColourer colourer = colourerOfSimpleTemporaryGraph();
		colourer.colour(10, 0);
		assertEquals("",colourer.toString());
	}
	
	@Test
	public void colouringGraphReturnsFalseIfKSmallerThanNumberOfRealRegs() {
		InterferenceGraphColourer colourer = colourerOfSimpleRealGraph();
		assertThat(colourer.colour(2, 0), is(false));
	}
	
	@Test
	public void colouringSlightlyMoreComplexGraphTest() {
		InterferenceGraphColourer colourer = 
				new InterferenceGraphColourer(bigTemporaryGraph());
		colourer.colour(4, 3);
		assertEquals("", colourer.toString());
	}
	
	private InterferenceGraphColourer colourerOfSimpleRealGraph() {
		return new InterferenceGraphColourer(simpleGraph());
	}
	
	private InterferenceGraphColourer colourerOfSimpleTemporaryGraph() {
		return new InterferenceGraphColourer(simpleTemporaryGraph());
	}

	private InterferenceGraphColourer colourerOfComplexGraph() {
		return new InterferenceGraphColourer(complexGraph());
	}
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private PseudoInstruction add = new Add(ArmRegister.r2, ArmRegister.r1, ArmRegister.r3);
	private BranchInstruction branchStart = new BranchInstruction(Condition.LT,new Label("start"));
	
	TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
	
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
	TemporaryRegister t11 = trg.generate(1);
	TemporaryRegister t12 = trg.generate(1);
	TemporaryRegister t13 = trg.generate(1);
	
	private PseudoInstruction movt = new Mov(t1, t2);
	private PseudoInstruction cmpt = new Cmp(t4, t3);
	private PseudoInstruction addt = new Add(t4, t1, t2);
	
	private PseudoInstruction large1 = new Mov(t1, t2);
	private PseudoInstruction large2 = new Mov(t3, t2);
	private PseudoInstruction large3 = new Add(t1, t2, t3);
	private PseudoInstruction large4 = new Mov(t2, t1);
	private PseudoInstruction large5 = new Mov(t6, t2);
	private PseudoInstruction large6 = new Add(t1, t2, t6);
	private PseudoInstruction large7 = new Mov(t10, t7);
	private PseudoInstruction large8 = new Sub(t10, t2, t6);

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
				large3
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
	
	private InterferenceGraph complexGraph() {
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
	
	private InterferenceGraph simpleGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				mov, cmp, add
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
	
	private InterferenceGraph simpleTemporaryGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				movt, cmpt, addt
				));

	    ControlFlowGraph cfg = new ControlFlowGraph(program);
		
		return new InterferenceGraph(cfg);
	}
}
