package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ReturnStatementAST extends ExprStatementAST {
	public ReturnStatementAST(ExprAST exprAST) {
		super(exprAST);
	}
	
	@Override
	protected String getName() {
		return "return";
	}

}
