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

public class LabelBabySiterTest {
	
	private CFGNode branch = new CFGNode(new BranchInstruction(null,new Label("l1")));
	private CFGNode mov = new CFGNode(new Mov(ArmRegister.r0, ArmRegister.r1));

	Map<Label,CFGNode> labelLookup = new HashMap<>();
	LabelBabySitter sitter = new LabelBabySitter(labelLookup);
	
	@Test
	public void canCheckThatEveryLabelNeedingInstructionIsSet() {
		assertThat(sitter.allInstructionsHappy(), is(true));
	}
	
	@Test
	public void canAddNonLabeldependantInsutrction() {
		sitter.add(mov);
		assertThat(sitter.allInstructionsHappy(), is(true));
	}
	
	@Test
	public void canAddLabeldependantInsutrction() {
	}
	@Test
	public void setsNextOfAllTheLookedafterNodesAccoringToLabelLookup() {
		
		sitter.add(branch);
		labelLookup.put(new Label("l1"),mov);
		assertThat(sitter.allInstructionsHappy(), is(true));
		assertThat(branch.getNext(), hasItems(mov));
	}
}