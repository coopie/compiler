package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.StatAST;


public abstract class ExprStatementAST extends StatAST {

	protected final ExprAST expr;

	public ExprStatementAST(ExprAST expr) {
		addStat(this);
		this.expr = expr;
	}
	
	protected abstract String getName();
	
	@Override
	public String toString() {
		return getName() + expr.toString();
	}
}