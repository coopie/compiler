package wacc.slack.instructions.visitors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Sub;

public class GetDefinedRegistersTest {

	Register reg0 = ArmRegister.r0;
	Register reg1 = ArmRegister.r1;
	Register destReg = ArmRegister.r2;

	@Test
	public void MovInstructionDefinitions() {
		assertThat(new Mov(reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(reg0));
	}

	// Binary ops

	@Test
	public void MulInstructionDefinitions() {
		assertThat(
				new Mul(destReg, reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(destReg));
	}

	@Test
	public void AddInstructionDefinitions() {
		assertThat(
				new Add(destReg, reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(destReg));
	}

	@Test
	public void SubInstructionDefinitions() {
		assertThat(
				new Sub(destReg, reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(destReg));
	}

	@Test
	public void CmpInstructionDefinitions() {
		assertThat(new Cmp(reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(reg0));
	}

	@Test
	public void AndInstructionDefinitions() {
		assertThat(
				new And(destReg, reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(destReg));
	}

	@Test
	public void OrrInstructionDefinitions() {
		assertThat(
				new Orr(destReg, reg0, reg1).accept(new GetDefinedRegisters()),
				hasItems(destReg));
	}

}
