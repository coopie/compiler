package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;

public class FreeStatementAST extends ExprStatementAST {
	
	public FreeStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	protected String getName() {
		return "free";
	}
}
