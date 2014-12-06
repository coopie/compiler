package wacc.slack.AST.types;

import wacc.slack.FilePosition;

// Called WaccArrayType to distinguish itself from the Java ArrayType object
public class WaccArrayType implements Type {

	private final Type type;

	public WaccArrayType(Type type) {
		this.type = type;
	}
	
	public WaccArrayType() {
		this(null);
	}

	@Override
	public FilePosition getFilePosition() {
		return null;
	}

	@Override
	public boolean equals(Object t) {
		return (t instanceof WaccArrayType)
				&& (type == null || ((WaccArrayType) t).getType() == null || type
						.equals(((WaccArrayType) t).getType()));
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		if (type.equals(BaseType.T_char)) {
			return "String";
		}
		return type.toString() + "[]";
	}
}
