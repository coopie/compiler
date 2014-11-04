package wacc.slack.AST.symbolTable;

import java.util.HashMap;
import java.util.Map;

//DO TDD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class SymbolTable<T> {
	
	//ref == *this* if it is a top scope symbolTable
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
		if(ref == this) {
			return table.get(ident);
		}
		
		if(!table.containsKey(ident)) {
			return ref.lookup(ident);
		} else {
			return table.get(ident);
		}
	}
	
	public SymbolTable<T> initializeNewScope() {
		return new SymbolTable<>(this);
	}
	
	public SymbolTable<T> popScope() {
		if(ref != this) {
			return ref;
		} else {
			throw new IllegalStateException("trying to pop top scope");
		}
	}
}
