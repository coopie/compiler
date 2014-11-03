package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class FreeStatementAST extends ExprStatementAST {
	public FreeStatementAST(ExprAST exprAST) {
		super(exprAST);
	}
	
	@Override
	protected String getName() {
		return "free";
	}
}
