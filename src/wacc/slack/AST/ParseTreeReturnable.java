package wacc.slack.AST;

import wacc.slack.FilePosition;

// Classes which implement this can be returned by the AST builder.
public interface ParseTreeReturnable {
	public FilePosition getFilePosition();
}
