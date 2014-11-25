package wacc.slack.controlFlow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractGraph<T> implements Iterable<T>  {

	private Map<T, Set<T>> graph = new HashMap<>();
	
	public Set<T> nodeSet() {
		return graph.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		return graph.keySet().iterator();
	}

	public Set<T> getAdjecent(T node) {
		return graph.get(node);
	}

	@Override
	public String toString() {
		return printGraph(graph);
	}
	
	public void putNode(T node, Set<T> adjecent) {
		if(graph.containsKey(node)) {
			graph.get(node).addAll(adjecent);
		} else {
			graph.put(node, adjecent);
		}
	}
	
	/**
	 * 
	 * @return adjecency list of this graph
	 */
	public Map<T,Set<T>> getAdjecencyList() {
		Map<T,Set<T>> g = new HashMap<>();
		for(T node : graph.keySet()) {
			g.put(node,new HashSet<>(graph.get(node)));
		}
		return g;
	}
	
	public static <T,R> String printGraph(Map<T,R> graph) {
		String s = "";
		
		for(T n : graph.keySet()) {
			s += n + ": " + graph.get(n) + "\n";
		}
		
		return s;
	}

}