package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayElem implements Assignable {

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

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		
	}

	@Override
	public String getName() {
		return ident;
	}
}
