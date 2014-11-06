package wacc.slack.AST.types;

import wacc.slack.FilePosition;
// called WaccArrayType to distinguish itself from the Java ArrayType object
public class WaccArrayType implements Type {

	private final Type type;
	
	public WaccArrayType(Type type) {
		this.type = type;
	}

	@Override
	public FilePosition getFilePosition() {
		return null;
	}
	
	public boolean equals(Type t) {
		return t instanceof WaccArrayType && type.equals(((WaccArrayType) t).type); 
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		if(type.equals(BaseType.T_char)) {
			return "String";
		}
		return type.toString() + "[]";
	}
}
