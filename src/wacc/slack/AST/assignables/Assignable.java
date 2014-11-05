package wacc.slack.AST.assignables;

import wacc.slack.AST.WaccAST;

public interface Assignable extends WaccAST, AssignRHS {
	public String getName();
}
