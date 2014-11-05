package wacc.slack.AST.symbolTable;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.Type;

public class IdentInfo {

	private final Type ident_type;
	private final FilePosition declaredAt;

	public IdentInfo(Type ident_type, FilePosition declaredAt) {
		this.ident_type = ident_type;
		this.declaredAt = declaredAt;
	}

	public Type getType() {
		return ident_type;
	}

	public FilePosition getDeclaredAt() {
		return declaredAt;
	}
	
}
