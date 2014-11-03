package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ReadStatementAST extends ExprStatementAST {
	
	public ReadStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}
	
	@Override
	protected String getName() {
		return "read";
	}

}
