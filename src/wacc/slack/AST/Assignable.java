package wacc.slack.AST;

public interface Assignable extends WaccAST {
	public String getName();
	public void checkType();
}
