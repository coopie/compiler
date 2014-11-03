package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;

public class ArrayElem implements ParseTreeReturnable {

	private final String ident;
	private final ExprAST expr;
	
	public ArrayElem(String ident, ExprAST expr) {
		this.ident = ident;
		this.expr = expr;
	}
	
	public String getIdent() {
		return ident;
	}
	
	public ExprAST getExpr() {
		return expr;
	}
}
