package wacc.slack.instructions.visitors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;

public class GetDefinedRegistersTest {
	
	Register reg0 = ArmRegister.r0;
	Register reg1 = ArmRegister.r1;
	String label = "label";
	Condition cond = Condition.AL;
	
	@Test
	public void MovInstructionDefinitions() {
		assertThat(new Mov(reg0,reg1).accept(new GetDefinedRegisters()), hasItems(reg0));
	}
	
	@Test
	public void LabelInstructionDefinitions() {
		assertTrue(new Label(label).accept(new GetDefinedRegisters()).isEmpty());
	}
	
	@Test
	public void BranchInstructionDefinitions() {
		assertTrue(new BranchInstruction(cond, new Label(label)).accept(new GetDefinedRegisters()).isEmpty());
	}
	
}
