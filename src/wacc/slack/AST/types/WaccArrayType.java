package wacc.slack.AST.types;

public class WaccArrayType implements Type {

	Type type;
	
	WaccArrayType(Type type) {
		this.type = type;
	}

	public boolean equals(Type t) {
		return t instanceof WaccArrayType && type.equals(((WaccArrayType) t).type); 
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getCharColumn() {
		// TODO Auto-generated method stub
		return -1;
	}
}
