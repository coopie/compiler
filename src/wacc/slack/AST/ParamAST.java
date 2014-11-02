package wacc.slack.AST;

import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ParamAST implements WaccAST{
	
	private String ident;
	private Type type;
	
	public ParamAST(String ident, Type type) {
		this.ident = ident;
		this.type = type;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public String getIdent() {
		return ident;
	}
	
	public Type getType() {
		return type;
	}
}
