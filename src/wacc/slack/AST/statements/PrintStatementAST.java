package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;

public class PrintStatementAST extends ExprStatementAST {
	
	public PrintStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	protected String getName() {
		return "print";
	}

}
