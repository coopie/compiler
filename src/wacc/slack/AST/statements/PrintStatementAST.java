package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class PrintStatementAST extends ExprStatementAST {
	public PrintStatementAST(ExprAST exprAST) {
		super(exprAST);
	}
	
	@Override
	protected String getName() {
		return "print";
	}

}
