package wacc.slack.instructions.visitors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
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
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;

public class GetUsedRegistersTest {

	Register reg0 = ArmRegister.r0;
	Register reg1 = ArmRegister.r1;
	Register destReg = ArmRegister.r2;

	String label = "label";
	Condition cond = Condition.AL;

	@Test
	public void MovInstructionUses() {
		assertThat(new Mov(reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg1));
	}

	// Binary ops

	@Test
	public void MulInstructionUses() {
		assertThat(new Mul(destReg, reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void AddInstructionUses() {
		assertThat(new Add(destReg, reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void SubInstructionUses() {
		assertThat(new Sub(destReg, reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void CmpInstructionUses() {
		assertThat(new Cmp(reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void AndInstructionUses() {
		assertThat(new And(destReg, reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void OrrInstructionUses() {
		assertThat(new Orr(destReg, reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg0, reg1));
	}

	@Test
	public void LabelInstructionUses() {
		assertTrue(new Label("label").accept(new GetUsedRegisters()).isEmpty());
	}

	@Test
	public void BranchInstructionUses() {
		assertTrue(new BranchInstruction(cond, new Label(label)).accept(
				new GetUsedRegisters()).isEmpty());
	}
	
	@Test
	public void StrInstructionUses() {
		assertThat(new Str(reg1, reg0).accept(new GetUsedRegisters()),
				hasItems(reg1, reg0));
	}
	
	@Test
	public void LdrInstructionDefintions() {
		assertThat(new Ldr(reg0, reg1).accept(new GetUsedRegisters()),
				hasItems(reg1));
	}
	
	@Test
	public void PushInstructionUses() {
		assertThat(new Push(reg0).accept(new GetUsedRegisters()),
				hasItems(reg0));
	}
	
	@Test
	public void PopInstructionUses() {
		assertThat(new Pop(reg0).accept(new GetUsedRegisters()),
				hasItems(reg0));
	}
	
}
