package wacc.slack.AST;

import org.antlr.v4.runtime.tree.ParseTree;

import antlr.WaccParser;

public interface ParserManipulator {
	public ParseTree manipulate(WaccParser p);
}
