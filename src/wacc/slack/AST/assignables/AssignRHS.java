package wacc.slack.AST.assignables;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public interface AssignRHS extends WaccAST {
	public Type getType();

}
