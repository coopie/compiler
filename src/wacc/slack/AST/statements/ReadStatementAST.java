package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ReadStatementAST extends ExprStatementAST {
	public ReadStatementAST(ExprAST exprAST) {
		super(exprAST);
	}
	
	@Override
	protected String getName() {
		return "read";
	}

}
