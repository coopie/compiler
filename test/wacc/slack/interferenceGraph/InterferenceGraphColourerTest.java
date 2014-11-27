package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

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
	
	private PseudoInstruction movt = new Mov(t1, t2);
	private PseudoInstruction cmpt = new Cmp(t4,t3);
	private PseudoInstruction addt = new Add(t4, t1, t2);
	
	
	
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
