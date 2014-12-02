package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static wacc.slack.interferenceGraph.ColouredGraphChecker.coloured;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Test;

import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Sub;

public class ColouredGraphCheckerTest {

	private InterferenceGraph ig = null;

	@Test
	public void uncolouredGraphFails() {
		ig = simplestGraph();
		assertThat(ig, is(not(coloured())));
	}

	@Test
	public void allButOneColouredFails() {
		ig = simplestGraph();
		int count = 1;
		for (InterferenceGraphNode n : ig) {
			if (count != 1) {
				n.colour(count);
			}
			count++;
		}
		assertThat(ig, is(not(coloured())));
	}
	
	@Test
	public void allButOneColouredWronlgyFails() {
		ig = bigTemporaryGraph();
		System.out.println(ig);
		int count = 1;
		for (InterferenceGraphNode n : ig) {
			if (count != 4) {
				n.colour(count);
			} else {
				for (InterferenceGraphNode neighbour : ig.getAdjecent(n)) {
					if(neighbour.isColoured()) {
						n.colour(neighbour.getColour());
					}
				}
			}
			count++;
		}
		assertThat(ig, is(not(coloured())));
	}

	@Test
	public void colouredGraphOfAllDifferentColoursPasses() {
		ig = simplestGraph();
		int count = 1;
		for (InterferenceGraphNode n : ig) {
			n.colour(count);
			count++;
		}
		assertThat(ig, is(coloured()));
	}

	@Test
	public void simpleColouredGraphTest() {
		ig = bigTemporaryGraph();

		for (InterferenceGraphNode n : ig) {
			for (int c = 0;; c++) {
				boolean neighboursContainThisColour = false;
				for (InterferenceGraphNode neighbour : ig.getAdjecent(n)) {
					if (c == neighbour.getColour()) {
						neighboursContainThisColour = true;
						break;
					}
				}
				if (!neighboursContainThisColour) {
					n.colour(c);
					break;
				}
			}
					}

		assertThat(ig, is(coloured()));
	}

	@After
	public void cleanGraph() {
		if (ig != null) {
			ig.clean();
		}
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

	private PseudoInstruction large1 = new Mov(t1, t2);
	private PseudoInstruction large2 = new Mov(t3, t2);
	private PseudoInstruction large3 = new Add(t1, t2, t3);
	private PseudoInstruction large4 = new Mov(t2, t1);
	private PseudoInstruction large5 = new Mov(t6, t2);
	private PseudoInstruction large6 = new Add(t1, t2, t6);
	private PseudoInstruction large7 = new Mov(t10, t7);
	private PseudoInstruction large8 = new Sub(t10, t2, t6);

	private InterferenceGraph bigTemporaryGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				large1, large2, large3, large4, large5, large6, large7, large8,
				large3));

		ControlFlowGraph cfg = new ControlFlowGraph(program);

		return new InterferenceGraph(cfg);
	}

	private InterferenceGraph simplestGraph() {
		Deque<PseudoInstruction> program = new LinkedList<>(Arrays.asList(
				large1, large2));

		ControlFlowGraph cfg = new ControlFlowGraph(program);

		return new InterferenceGraph(cfg);
	}

}
