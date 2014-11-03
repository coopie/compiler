package wacc.slack.AST.types;

public class ArrayType implements Type {

	private final Type type;
	
	public ArrayType(Type type) {
		this.type = type;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharColumn() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean equals(Type t) {
		return t instanceof ArrayType && type.equals(((ArrayType) t).type); 
	}
	
	public Type getType() {
		return type;
	}
}
