package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Test;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;


public class TemporaryRegisterMappingsTest {
	
	private InterferenceGraph ig;
	
	@After
	public void cleanGraph() {
		if (ig != null) {
			ig.clean();
		}
	}
	
	@Test
	public void largeGraphColuringWithOnlyTemporaries() {
		bigTemporaryGraph();
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		TemporaryRegisterMapping trm = colourer.generateTemporaryRegisterMappings(3, true);
		
		System.out.println(trm);
		
		assertThat(trm.getResolvedRegisters().keySet(), hasItems(t10, t3, t1, t6, t2));
		assertThat(trm.getSpilledRegisters().keySet(), hasItems(t7));
	}
	
	
	@Test
	public void nodesConnectedToEachOtherCannotHaveTheSameColouring() {
		bigTemporaryGraph();
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		TemporaryRegisterMapping trm = colourer.generateTemporaryRegisterMappings(5, false);
		
		assertThat(trm.getResolvedRegisters().get(t2), is(not(trm.getResolvedRegisters().get(t10))));
		assertThat(trm.getResolvedRegisters().get(t1), is(not(trm.getResolvedRegisters().get(t2))));
		assertThat(trm.getResolvedRegisters().get(t2), is(not(trm.getResolvedRegisters().get(t3))));
		assertThat(trm.getResolvedRegisters().get(t2), is(not(trm.getResolvedRegisters().get(t6))));
	}
	
	@Test
	public void resolvedRegistersHaveOnlyKElementsInThem() {
		bigTemporaryGraph();
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		TemporaryRegisterMapping trm = colourer.generateTemporaryRegisterMappings(3, true);
		
		assertThat(trm.getResolvedRegisters().keySet(), hasItems(t1, t2, t3, t6, t10));
		assertThat(trm.getResolvedRegisters().size(), is(5));
	}
	
	@Test
	public void SpilledRegistersHaveTheRestOfTheRegistersInThem() {
		bigTemporaryGraph();
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		TemporaryRegisterMapping trm = colourer.generateTemporaryRegisterMappings(3, true);
		
		System.out.println(trm);
		
		assertThat(trm.getSpilledRegisters().size(), is(1));
		assertThat(trm.getSpilledRegisters().keySet(), hasItems(t7));
	}
	
	@Test
	public void graphContainingRealRegistersDoesntIncludeThemInMapping() {
		bigMixedGraph();
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		TemporaryRegisterMapping trm = colourer.generateTemporaryRegisterMappings(3, true);
		
		System.out.println(trm);
		
		assertThat(trm.getSpilledRegisters().size(), is(1));
		assertThat(trm.getSpilledRegisters().keySet(), hasItems(t7));
	}

	
	
	

	TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();

	TemporaryRegister t1 = trg.generate(1);
	TemporaryRegister t2 = trg.generate(1);
	TemporaryRegister t3 = trg.generate(1);
	TemporaryRegister t4 = trg.generate(1);
	TemporaryRegister t5 = trg.generate(1);
	TemporaryRegister t6 = trg.generate(1);
	TemporaryRegister t7 = trg.generate(1);
	TemporaryRegister t8 = trg.generate(1);
	TemporaryRegister t9 = trg.generate(1);
	TemporaryRegister t10 = trg.generate(1);
	TemporaryRegister t11 = trg.generate(1);
	TemporaryRegister t12 = trg.generate(1);
	TemporaryRegister t13 = trg.generate(1);

	private PseudoInstruction movt = new Mov(t1, t2);
	private PseudoInstruction cmpt = new Cmp(t4, t3);
	private PseudoInstruction addt = new Add(t4, t1, t2);
	
	private PseudoInstruction large1 = new Mov(t1, t2);
	private PseudoInstruction large2 = new Mov(t3, t2);
	private PseudoInstruction large3 = new Add(t1, t2, t3);
	private PseudoInstruction large4 = new And(t2, t1, t6);
	private PseudoInstruction large5 = new Mov(t6, t2);
	private PseudoInstruction large6 = new Mul(t1, t2, t6);
	private PseudoInstruction large7 = new Orr(t10, t7, t1);
	private PseudoInstruction large8 = new Sub(t10, t2, t6);
	private PseudoInstruction large9 = new Push(t2);
	private PseudoInstruction large10 = new Sub(t10, t2, t6);
	private PseudoInstruction large11 = new Label("blah");

	private PseudoInstruction largeEnd = new Str(new Address(t2, 3), t10);

	private InterferenceGraph bigTemporaryGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				large1,
				large2,
				large3,
				large4,
				large5,
				large6,
				large7, 
				large8,
				large9, 
				large10,
				large11,
				largeEnd));
		ControlFlowGraph cfg = new ControlFlowGraph(program);

		ig = new InterferenceGraph(cfg);
		return ig;
	}
	
	private PseudoInstruction test1 = new Mov(ArmRegister.r1, ArmRegister.r2);
	private PseudoInstruction test2 = new Mov(t3, ArmRegister.r2);
	private PseudoInstruction test3 = new Add(ArmRegister.r1, ArmRegister.r2, t3);
	private PseudoInstruction test4 = new And(ArmRegister.r2, ArmRegister.r1, t6);
	private PseudoInstruction test5 = new Mov(t6, ArmRegister.r2);
	private PseudoInstruction test6 = new Mul(ArmRegister.r1, ArmRegister.r2, t6);
	private PseudoInstruction test7 = new Orr(t10, t7, ArmRegister.r1);
	private PseudoInstruction test8 = new Sub(t10, ArmRegister.r2, t6);
	private PseudoInstruction test9 = new Push(ArmRegister.r2);
	private PseudoInstruction test10 = new Sub(t10, ArmRegister.r2, t6);
	private PseudoInstruction test11 = new Label("blah");
	
	private PseudoInstruction testEnd = new Str(new Address(ArmRegister.r2, 3), t10);
	
	private InterferenceGraph bigMixedGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				test1,
				test2,
				test3,
				test4,
				test5,
				test6,
				test7, 
				test8,
				test9, 
				test10,
				test11,
				testEnd));
		ControlFlowGraph cfg = new ControlFlowGraph(program);

		ig = new InterferenceGraph(cfg);
		return ig;
	}
	
}
