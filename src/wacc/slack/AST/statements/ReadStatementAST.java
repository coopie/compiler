package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.StatAST;

public class ReadStatementAST extends StatAST {

	private final ExprAST expr;

	public ReadStatementAST(ExprAST expr) {
		addStat(this);
		this.expr = expr;
	}

}
