package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;

public class PrintStatementAST extends ExprStatementAST {
	public PrintStatementAST(ExprAST expr) {
		super(expr);
	}
	
	@Override
	protected String getName() {
		return "print";
	}

}
