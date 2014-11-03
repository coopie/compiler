package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class PrintlnStatementAST extends ExprStatementAST {
	
	public PrintlnStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}
	
	@Override
	protected String getName() {
		return "println";
	}
}
