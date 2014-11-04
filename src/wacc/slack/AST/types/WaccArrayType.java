package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public class WaccArrayType implements Type {

	Type type;
	
	WaccArrayType(Type type) {
		this.type = type;
	}

	public boolean equals(Type t) {
		return t instanceof WaccArrayType && type.equals(((WaccArrayType) t).type); 
	}

	@Override
	public FilePosition getFilePosition() {
		return null;
	}
}
