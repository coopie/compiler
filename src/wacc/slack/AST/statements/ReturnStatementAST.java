package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;

public class ReturnStatementAST extends ExprStatementAST {
	
	public ReturnStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	public String getName() {
		return "return";
	}

}
