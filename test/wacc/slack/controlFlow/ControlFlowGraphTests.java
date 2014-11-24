package wacc.slack.controlFlow;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;

public class ControlFlowGraphTests {
	
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);

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
	
}
