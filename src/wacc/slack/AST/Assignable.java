package wacc.slack.AST;

public interface Assignable extends WaccAST, AssignRHS {
	public String getName();
	public void checkType();
}
