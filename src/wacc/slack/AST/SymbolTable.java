package wacc.slack.AST;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<T> {
	
	private SymbolTable<T> ref;
	private Map<String,T> table = new HashMap<>();
	
	public SymbolTable() {
		ref = this;
	}
	
	public SymbolTable(SymbolTable<T> r) {
		ref = r;
	}
	
	public void insert(String s, T s1) {
		table.put(s, s1);
	}
	public T lookup(String ident) {
		return table.get(ident);
	}
	
	public SymbolTable<T> initializeNewScope() {
		return new SymbolTable<>(this);
	}
	
	public SymbolTable<T> popScope() {
		if(ref != this) {
			return ref;
		} else {
			assert false: "poping top scope";
		}
		return null;
	}
}
