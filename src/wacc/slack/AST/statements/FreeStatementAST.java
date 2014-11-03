package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class FreeStatementAST extends ExprStatementAST {
	
	public FreeStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}
	
	@Override
	protected String getName() {
		return "free";
	}
}
