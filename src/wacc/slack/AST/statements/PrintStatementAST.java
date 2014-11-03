package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class PrintStatementAST extends ExprStatementAST {
	
	public PrintStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}
	
	@Override
	protected String getName() {
		return "print";
	}

}
