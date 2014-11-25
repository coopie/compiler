package wacc.slack.controlFlow;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class AbstractGraphTests {
	
	private AbstractGraph<Integer> graph = new AbstractGraph<Integer>() {
	};
	
	@Test
	public void canInsertNode() {
		graph.putNode(1, new HashSet<Integer>());
		assertEquals("1: []\n",graph.toString());
	}
	
	@Test
	public void canInsertAnEdge() {
		graph.putNode(1, new HashSet<Integer>(Arrays.asList(1)));
		assertEquals("1: [1]\n",graph.toString());
	}
	
	@Test
	public void canInsertMoreNodes() {
		graph.putNode(1, new HashSet<Integer>(Arrays.asList(1)));
		graph.putNode(2, new HashSet<Integer>(Arrays.asList(1,3)));
		graph.putNode(3, new HashSet<Integer>(Arrays.asList(3,2)));
		assertEquals("1: [1]\n2: [1, 3]\n3: [2, 3]\n",graph.toString());
	}
	
	@Test
	public void puttingANodeTwiceAddsTheedges() {
		graph.putNode(1, new HashSet<Integer>(Arrays.asList(1)));
		graph.putNode(1, new HashSet<Integer>(Arrays.asList(1,3)));
		assertEquals("1: [1, 3]\n",graph.toString());
		
	}
	
	@Test
	public void canGetAdjecencyList() {
		graph.putNode(1, new HashSet<Integer>(Arrays.asList(1)));
		graph.putNode(2, new HashSet<Integer>(Arrays.asList(1,3)));
		graph.putNode(3, new HashSet<Integer>(Arrays.asList(3,2)));
	
		Map<Integer, Set<Integer>> adjecencyList = graph.getAdjecencyList();
		adjecencyList.remove(3);

		assertEquals("1: [1]\n2: [1, 3]\n3: [2, 3]\n",graph.toString());
		assertEquals("1: [1]\n2: [1, 3]\n",AbstractGraph.printGraph(adjecencyList));
	}
	
	
}
