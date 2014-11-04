package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;

public class PrintlnStatementAST extends ExprStatementAST {
	
	public PrintlnStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	protected String getName() {
		return "println";
	}
}
