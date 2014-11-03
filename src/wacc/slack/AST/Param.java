package wacc.slack.AST;

import wacc.slack.AST.types.Type;

public class Param implements ParseTreeReturnable {
	
	private final String ident;
	private final Type type;
	private final int linePos, charPos;
	
	public Param(String ident, Type type, int linePos, int charPos) {
		this.ident = ident;
		this.type = type;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	public String getIdent() {
		return ident;
	}
	
	public Type getType() {
		return type;
	}
}
