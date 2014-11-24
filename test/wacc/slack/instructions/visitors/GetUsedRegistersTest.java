package wacc.slack.instructions.visitors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;

public class GetUsedRegistersTest {
	
	Register reg0 = ArmRegister.r0;
	Register reg1 = ArmRegister.r1;
	
	@Test
	public void MovInstructionDefinitions() {
		assertThat(new Mov(reg0,reg1).accept(new GetUsedRegisters()), hasItems(reg1));
	}
}
