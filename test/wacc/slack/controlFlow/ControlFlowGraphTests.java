package wacc.slack.controlFlow;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static wacc.slack.controlFlow.AbstractGraph.graphEquals;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;
public class ControlFlowGraphTests {
	
	private BranchInstruction branch = new BranchInstruction(Condition.AL,new Label("l1"));
	private PseudoInstruction mov = new Mov(ArmRegister.r0, ArmRegister.r1);
	private PseudoInstruction cmp = new Cmp(ArmRegister.r0,ArmRegister.r1);
	private BranchInstruction branchStart = new BranchInstruction(Condition.LT,new Label("start"));
	
	TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
	
	TemporaryRegister t1 = trg.generate(1);
	TemporaryRegister t2 = trg.generate(1);
	TemporaryRegister t3 = trg.generate(1);
	TemporaryRegister t4 = trg.generate(1);
	
	private final PseudoInstruction movt = new Mov(t1, t2);
	private final PseudoInstruction cmpt = new Cmp(t4,t3);
	private final PseudoInstruction addt = new Add(t4, t1, t2);
	private final PseudoInstruction ldrt = new Ldr(t2, t1);
	private final Label whileStart = new Label("l1");
	private final PseudoInstruction whileEnd = new BranchInstruction(Condition.GE, whileStart);
	
	Deque<PseudoInstruction> programUsingTemporaries = new LinkedList<>(Arrays.asList(
			movt,
			cmpt,
			addt
			));
	Map<CFGNode,Set<Register>> liveOutForProgramUsingTemporaries = new HashMap<CFGNode,Set<Register>>(){{
		put(new CFGNode(2,movt),new HashSet<>(Arrays.asList((Register)t3,t4,t1,t2)) );
		put(new CFGNode(1,cmpt),new HashSet<>(Arrays.asList((Register)t2,t1)) );
		put(new CFGNode(0,addt),new HashSet<Register>());
			
	}};
	
	Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
			branch,
			new Label("start"),
			mov,
			new Label("l1"),
			cmp,
			branchStart,
			branch
			));
	

	@Test
	public void gettingLiveOutIsDeterministic() {
		ControlFlowGraph cfg = new ControlFlowGraph(programUsingTemporaries);

		assertTrue(graphEquals(new ControlFlowGraph(programUsingTemporaries).getLiveOut(),
				new ControlFlowGraph(programUsingTemporaries).getLiveOut()));
	}
	
	@Test
	public void liveOutCorrectForPorgramUsingTemporaries() {
		assertTrue(graphEquals(new ControlFlowGraph(programUsingTemporaries).getLiveOut(),liveOutForProgramUsingTemporaries));
	
	}
	
	@Test
	public void liveOutCorrectForWhileStartMovCmpAddWhileEndLdr() {
		
		assertTrue(graphEquals(new ControlFlowGraph(new LinkedList<>(Arrays.asList(whileStart,movt,cmpt,addt,whileEnd,ldrt))).getLiveOut(),
				new HashMap<CFGNode,Set<Register>>() {{
				  put(new CFGNode(4,movt),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3,t4)));	
				  put(new CFGNode(3,cmpt),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3)));	
				  put(new CFGNode(2,addt),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3,t4)));	
				  put(new CFGNode(1,whileEnd),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3,t4)));	
				  put(new CFGNode(0,ldrt),new HashSet<Register>());	
					
				}}
				));
		
	}
	
	@Test
	public void canUseEqualsForSetsOfCFGNodes() {
		assertThat(new HashSet<CFGNode>(Arrays.asList(new CFGNode(0,movt),new CFGNode(1,cmpt)))
				.equals(
				   new HashSet<CFGNode>(Arrays.asList(new CFGNode(0,movt),new CFGNode(1,cmpt)))),
				is(true));
	}
	
	@Test
	public void canUseEqualsForKeySetsOfMaps() {
		Map<CFGNode,Set<Register>> m1 =	new HashMap<CFGNode,Set<Register>>() {{
				  put(new CFGNode(0,movt),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3,t4)));	
				  put(new CFGNode(1,cmpt),new HashSet<Register>(Arrays.asList((Register)t1,t2)));	
				}};
	
		Map<CFGNode,Set<Register>> m2 =	new HashMap<CFGNode,Set<Register>>() {{
				put(new CFGNode(0,movt),new HashSet<Register>(Arrays.asList((Register)t1,t2,t3,t4)));	
				put(new CFGNode(1,cmpt),new HashSet<Register>(Arrays.asList((Register)t1,t2)));	
			}};
		
	    assertTrue(m1.keySet().equals(m2.keySet()));
		
	}
	
	@Test
	public void canCreateCFGNodeWithPseudoInstruction() {
		CFGNode n = new CFGNode(1,mov);
		assertThat(n.getInstruction(),is(mov));
	}
	
	@Test
	public void canGetRegistersDefinedByInstruction() {
		CFGNode n = new CFGNode(1,mov);
		assertThat(n.getDefinitions(),hasItems((Register)ArmRegister.r0));
	}
	
	@Test
	public void canGetRegistersUsedByInstruction() {
		CFGNode n = new CFGNode(1,mov);
		assertThat(n.getUses(),hasItems((Register)ArmRegister.r1));
	}
	
	
	@Test
	public void canMakeGraph() {
		ControlFlowGraph graph = new ControlFlowGraph(new LinkedList<>(Arrays.asList(cmp,mov)));
		assertThat(graph.nodeSet().size(), is(2));
	
	}
	
	@Test
	public void cFGGraphCanHaveOneNextNode() {
		ControlFlowGraph graph = new ControlFlowGraph(new LinkedList<>(Arrays.asList(cmp,mov)));
			
		assertThat(graph.nodeSet().size(),is(2));
		for(CFGNode n : graph) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.getAdjecent(n).size(),is(1));
				assertThat(graph.getAdjecent(n).iterator().next().getInstruction(), is(mov));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.getAdjecent(n).size(),is(0));
			} else {
				assertFalse(true);
			}
		}
	}
	
	@Test
	public void cFGGraphCanIncludeLabels() {
		ControlFlowGraph graph = new ControlFlowGraph(new LinkedList<>(Arrays.asList(branch,new Label("l1"),mov)));	
		assertThat(graph.nodeSet().size(),is(2));
	}

	@Test
	public void branchInstructionHasOneNext() {
		ControlFlowGraph graph = new ControlFlowGraph(new LinkedList<>(Arrays.asList(new Label("l1"),mov,branch,cmp)));	

		assertThat(graph.nodeSet().size(),is(3));
		for(CFGNode n : graph) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.getAdjecent(n).size(),is(0));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.getAdjecent(n).size(),is(1));
			} else if (n.getInstruction() == branch) {
				assertThat(graph.getAdjecent(n).size(),is(1));
				assertThat(graph.getAdjecent(n).iterator().next().getInstruction(), is(mov));
			} else {
				assertFalse(true);
			}
		}
	}

	@Test
	public void branchInstructionHasTwoNexts() {
		ControlFlowGraph graph = new ControlFlowGraph(new LinkedList<>(Arrays.asList(new Label("start"),mov,branchStart,cmp)));	

		assertThat(graph.nodeSet().size(),is(3));
		for(CFGNode n : graph) {
			if(n.getInstruction() == cmp) {
				assertThat(graph.getAdjecent(n).size(),is(0));
			} else if (n.getInstruction() == mov) {
				assertThat(graph.getAdjecent(n).size(),is(1));
			} else if (n.getInstruction() == branchStart) {
				assertThat(graph.getAdjecent(n).size(),is(2));
			} else {
				assertFalse(true);
			}
		}
	}
	
	@Test
	public void isChangedWorksSame() {
		ControlFlowGraph cfg = new ControlFlowGraph(new LinkedList<PseudoInstruction>());
		
		Map<Integer, Set<String>> thingy = new HashMap<>();
		thingy.put(1, new HashSet<String>(Arrays.asList("fs", "fsf")));
		thingy.put(2, new HashSet<String>(Arrays.asList("fs", "fsf")));
		
		assertTrue(cfg.isChanged(thingy, thingy));
		assertTrue(cfg.isChanged(thingy, thingy));
		assertTrue(cfg.isChanged(thingy, thingy));
		assertTrue(cfg.isChanged(thingy, thingy));
		assertFalse(cfg.isChanged(thingy, thingy));
		
		
	}
	
	@Test
	public void isChangedWorksDifferent() {
		ControlFlowGraph cfg = new ControlFlowGraph(new LinkedList<PseudoInstruction>());
		Map<Integer, Set<String>> thingy = new HashMap<>();
		thingy.put(1, new HashSet<String>(Arrays.asList("fsfsdffwgrs", "fsf")));
		Map<Integer, Set<String>> thingy2 = new HashMap<>(thingy);
		
		thingy2.put(2, new HashSet<String>(Arrays.asList("fs", "fsf")));
		
		
		assertFalse(cfg.isChanged(thingy2, thingy));
		
		
	}
}
