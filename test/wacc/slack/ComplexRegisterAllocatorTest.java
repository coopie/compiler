package wacc.slack;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Deque;
import java.util.Iterator;

import org.junit.Test;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.Eor;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.visitors.ComplexRegisterAllocator;
import wacc.slack.instructions.visitors.GenerateAssembly;
import wacc.slack.instructions.visitors.GenerateAssemblyBuilder;
import wacc.slack.instructions.visitors.InstructionVistor;

public class ComplexRegisterAllocatorTest {

	private  InstructionVistor<Deque<PseudoInstruction>> allocator = new ComplexRegisterAllocator();
	private GenerateAssembly printer = new GenerateAssemblyBuilder().make();

	TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
	
	TemporaryRegister t1 = trg.generate(1);
	TemporaryRegister t2 = trg.generate(1);
	TemporaryRegister t3 = trg.generate(1);
	TemporaryRegister t4 = trg.generate(1);
	
	@Test
	public void doesChangeLdrRegisterOffests() {
		PseudoInstruction ldr = new Ldr(t1,new Address(t2,t3));
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("LDR r7 [r7 ,r8]"));	
		instr = i.next();
	}
	
	@Test
	public void doesChangeLdrRegisterImediateValue() {
		PseudoInstruction ldr = new Ldr(t1,new Address(t2,4));
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(3));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		assertThat(instr.toString(), is("LDR r7 [r7 ,4]"));	
		instr = i.next();
	}
	
	@Test
	public void doesChangeStrRegisterOffests() {
		PseudoInstruction ldr = new Str(t1,new Address(t2,t3));
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("STR r7 [r8 ,r9]"));	
		
	}
	
	@Test
	public void doesChangeStrreggisterImediateValue() {
		PseudoInstruction ldr = new Str(t1,new Address(t2,4));
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(3));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("STR r7 [r8 ,4]"));	
		
	}
	
	@Test
	public void doesChangeEor() {
		PseudoInstruction ldr = new Eor(t1,t2,t3);
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.accept(printer).replaceAll("[\\t\\n]+", ""), is("EOR r9, r7, r8"));	
		
	}
	
	@Test
	public void doesChangeAnd() {
		PseudoInstruction ldr = new And(t1,t2,t3);
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.accept(printer).replaceAll("[\\t\\n]+", ""), is("AND r9, r7, r8"));	
		
	}
	
	@Test
	public void doesChangeAdd() {
		PseudoInstruction ldr = new Add(t1,t2,t3);
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.accept(printer).replaceAll("[\\t\\n]+", ""), is("ADD r9, r7, r8"));	
		
	}
	
	@Test
	public void doesChangeAddWith2Arguemnts() {
		PseudoInstruction ldr = new Add(t1,t2);
		Deque<PseudoInstruction> l = ldr.accept(allocator);
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.accept(printer).replaceAll("[\\t\\n]+", ""), is("ADD r9, r7, r8"));		
		
	}
}
