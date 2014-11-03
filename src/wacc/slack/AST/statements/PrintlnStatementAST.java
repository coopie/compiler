package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class PrintlnStatementAST extends ExprStatementAST {
	public PrintlnStatementAST(ExprAST exprAST) {
		super(exprAST);
	}
	
	@Override
	protected String getName() {
		return "println";
	}
}
