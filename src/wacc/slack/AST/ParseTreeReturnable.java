package wacc.slack.AST;

// Classes which implement this can be returned by the AST builder.

public interface ParseTreeReturnable {
	public int getLine();
	public int getCharColumn();
}
