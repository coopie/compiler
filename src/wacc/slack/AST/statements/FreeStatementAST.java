package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class FreeStatementAST extends ExprStatementAST {
	public FreeStatementAST(ExprAST expr) {
		super(expr);
	}
	
	@Override
	protected String getName() {
		return "free";
	}
}
