package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;

public class ExitStatementAST extends ExprStatementAST {
	
	public ExitStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(exprAST, linePos, charPos);
	}

	@Override
	protected String getName() {
		return "exit";
	}

}
