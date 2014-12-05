package wacc.slack.AST.symbolTable;

import java.util.HashMap;
import java.util.Map;

import wacc.slack.FilePosition;

public class SymbolTable<T extends IdentInfo> {

	// ref == *this* if it is a top scope symbolTable
	private SymbolTable<T> ref;
	private Map<String, T> table = new HashMap<>();

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
		if (ref == this) {
			return table.get(ident);
		}

		if (!table.containsKey(ident)) {
			return ref.lookup(ident);
		} else {
			return table.get(ident);
		}
	}

	public IdentInfo lookup(String ident, FilePosition fp) {
		IdentInfo i;
		if (ref == this) {
			return table.get(ident);
		}

		if (!table.containsKey(ident)) {
			return ref.lookup(ident, fp);
		} else {
			i = table.get(ident);
			if (ref.lookup(ident, fp) != null) {
				// if the declaration in the scope is after where we are looking
				// up
				if (i.getDeclaredAt().compareTo(fp) > 0) {
					return ref.lookup(ident, fp);
				} else {
					return i;
				}
			} else {

			}
		}
		throw new RuntimeException("problem with the symbolTable lookup for "
				+ ident + " at: " + fp);

	}

	public SymbolTable<T> initializeNewScope() {
		return new SymbolTable<>(this);
	}

	public SymbolTable<T> popScope() {
		if (ref != this) {
			return ref;
		} else {
			throw new IllegalStateException("trying to pop top scope");
		}
	}

	public T lookupCurrentScope(String key) {
		return table.get(key);
	}
}
