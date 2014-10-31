package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.StatAST;

public class ExitStatementAST extends StatAST {
	private final ExprAST expr;

	public ExitStatementAST(ExprAST expr) {
		addStat(this);
		this.expr = expr;
		// TODO Auto-generated constructor stub
	}

}
