package wacc.slack.interferenceGraph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
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
		InterferenceGraphColourer colourer = colourerOfSimpleGraph();
		colourer.colour(10, 0);
		assertThat(colourer.toString(),
				is(""));
	}
	
	private InterferenceGraphColourer colourerOfSimpleGraph() {
		return new InterferenceGraphColourer(simpleGraph());
	}
	
	private InterferenceGraphColourer colourerOfComplexGraph() {
		return new InterferenceGraphColourer(complexGraph());
	}
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private PseudoInstruction add = new Add(ArmRegister.r2, ArmRegister.r1, ArmRegister.r3);
	private BranchInstruction branchStart = new BranchInstruction(Condition.LT,new Label("start"));
	
	
	
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
}
