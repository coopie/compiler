package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class ReadStatementAST extends ExprStatementAST {
	public ReadStatementAST(ExprAST expr) {
		super(expr);
	}
	
	@Override
	protected String getName() {
		return "read";
	}

}
