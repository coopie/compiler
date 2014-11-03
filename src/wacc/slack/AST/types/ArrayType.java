package wacc.slack.AST.types;

public class ArrayType implements Type {

	Type type;
	
	ArrayType(Type type) {
		this.type = type;
	}

	public boolean equals(Type t) {
		return t instanceof ArrayType && type.equals(((ArrayType) t).type); 
	}
}
