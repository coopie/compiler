package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ReturnStatementAST extends ExprStatementAST {
	public ReturnStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}
	
	@Override
	protected String getName() {
		return "return";
	}

}
