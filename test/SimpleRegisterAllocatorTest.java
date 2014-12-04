import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Deque;
import java.util.Iterator;

import org.junit.Test;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.visitors.SimpleRegisterAllocator;

public class SimpleRegisterAllocatorTest {

TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
	
	TemporaryRegister t1 = trg.generate(1);
	TemporaryRegister t2 = trg.generate(1);
	TemporaryRegister t3 = trg.generate(1);
	TemporaryRegister t4 = trg.generate(1);
	
	@Test
	public void doesChangeLdrRegisterOffests() {
		PseudoInstruction ldr = new Ldr(t1,new Address(t2,t3));
		Deque<PseudoInstruction> l = ldr.accept(new SimpleRegisterAllocator());
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("LDR r7 [r8 ,r9]"));	
		instr = i.next();
	}
	
	@Test
	public void doesChangeLdrRegisterImediateValue() {
		PseudoInstruction ldr = new Ldr(t1,new Address(t2,4));
		Deque<PseudoInstruction> l = ldr.accept(new SimpleRegisterAllocator());
		
		assertThat(l.size(), is(3));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		assertThat(instr.toString(), is("LDR r7 [r8 ,4]"));	
		instr = i.next();
	}
	
	@Test
	public void doesChangeStrRegisterOffests() {
		PseudoInstruction ldr = new Str(t1,new Address(t2,t3));
		Deque<PseudoInstruction> l = ldr.accept(new SimpleRegisterAllocator());
		
		assertThat(l.size(), is(4));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("STR r9 [r8 ,r7]"));	
		
	}
	
	@Test
	public void doesChangeStrreggisterImediateValue() {
		PseudoInstruction ldr = new Str(t1,new Address(t2,4));
		Deque<PseudoInstruction> l = ldr.accept(new SimpleRegisterAllocator());
		
		assertThat(l.size(), is(3));
		Iterator<PseudoInstruction> i = l.iterator();
		
		PseudoInstruction instr = i.next();
		
		instr = i.next();
		instr = i.next();
		assertThat(instr.toString(), is("STR r9 [r8 ,4]"));	
		
	}
}
