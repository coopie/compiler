package wacc.slack.AST;

import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class Param implements ParseTreeReturnable {
	
	private String ident;
	private Type type;
	
	public Param(String ident, Type type) {
		this.ident = ident;
		this.type = type;
	}

 String getIdent() {
		return ident;
	}
	
	public Type getType() {
		return type;
	}
}
