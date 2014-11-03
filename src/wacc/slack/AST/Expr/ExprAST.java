package wacc.slack.AST.Expr;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public interface ExprAST extends WaccAST {

	public Type getType();
	
}
