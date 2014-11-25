package wacc.slack.controlFlow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractGraph<T,R> implements Iterable<T>  {

	private Map<T, Set<R>> graph = new HashMap<>();
	
	public Set<T> nodeSet() {
		return graph.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		return graph.keySet().iterator();
	}

	public Set<R> getAdjecent(T node) {
		return graph.get(node);
	}

	@Override
	public String toString() {
		return printGraph();
	}
	
	protected void putNode(T node, Set<R> adjecent) {
		graph.put(node, adjecent);
	}
	
	/**
	 * 
	 * @return adjecency list of this graph
	 */
	public Map<T,Set<R>> getAdjecencyList() {
		Map<T,Set<R>> g = new HashMap<>();
		for(T node : graph.keySet()) {
			g.put(node,new HashSet<>(graph.get(node)));
		}
		return g;
	}
	
	public String printGraph() {
		String s = "";
		
		for(T n : graph.keySet()) {
			s += n + ": " + graph.get(n) + "\n";
		}
		
		return s;
	}

}