package wacc.slack.AST.literals;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public interface LiterAST extends WaccAST {

	public Type getType();
}
