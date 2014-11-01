package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class ExitStatementAST extends ExprStatementAST {
	public ExitStatementAST(ExprAST expr) {
		super(expr);
	}

	@Override
	protected String getName() {
		return "exit";
	}

}
