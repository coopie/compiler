package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class ReturnStatementAST extends ExprStatementAST {
	public ReturnStatementAST(ExprAST expr) {
		super(expr);
	}
	
	@Override
	protected String getName() {
		return "return";
	}

}
