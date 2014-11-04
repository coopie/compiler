package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public class ArrayType implements Type {

	private final Type type;
	
	public ArrayType(Type type) {
		this.type = type;
	}

	@Override
	public FilePosition getFilePosition() {
		return null;
	}
	
	public boolean equals(Type t) {
		return t instanceof ArrayType && type.equals(((ArrayType) t).type); 
	}
	
	public Type getType() {
		return type;
	}
}
