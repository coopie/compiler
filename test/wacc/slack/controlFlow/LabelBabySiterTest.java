package wacc.slack.controlFlow;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;

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
		sitter.add(mov, new HashSet<CFGNode>());
		assertThat(sitter.allInstructionsHappy(), is(true));
	}
	
	@Test
	public void canAddLabeldependantInsutrction() {
		sitter.add(mov,null);
		assertThat(sitter.allInstructionsHappy(), is(true));
		
	}
	@Test
	public void setsNextOfAllTheLookedAfterNodesAccoringToLabelLookup() {
		
		Set<CFGNode> nexts = new HashSet<>();
		sitter.add(branch,nexts);
		labelLookup.put(new Label("l1"),mov);
		assertThat(sitter.allInstructionsHappy(), is(true));
		assertThat(nexts, hasItems(mov));
	}
}