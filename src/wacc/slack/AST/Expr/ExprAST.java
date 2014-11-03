package wacc.slack.AST.Expr;

import wacc.slack.AST.AssignRHS;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public interface ExprAST extends WaccAST, AssignRHS {

	public Type getType();
	
}
