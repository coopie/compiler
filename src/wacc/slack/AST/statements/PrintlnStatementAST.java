package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class PrintlnStatementAST extends ExprStatementAST {
	public PrintlnStatementAST(ExprAST expr) {
		super(expr);
	}
	
	@Override
	protected String getName() {
		return "println";
	}
}
