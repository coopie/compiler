package wacc.slack.AST.assignables;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public interface Assignable extends WaccAST, AssignRHS {
	public String getName();

	public Type getType();
}
