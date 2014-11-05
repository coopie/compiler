package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;

public class ExitStatementAST extends ExprStatementAST {

	public ExitStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	public String getName() {
		return "exit";
	}

}
