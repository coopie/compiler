package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ExitStatementAST extends ExprStatementAST {
	public ExitStatementAST(ExprAST exprAST) {
		super(exprAST);
	}

	@Override
	protected String getName() {
		return "exit";
	}

}
