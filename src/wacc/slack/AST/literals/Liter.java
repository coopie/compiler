package wacc.slack.AST.literals;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.types.Type;

public interface Liter extends ParseTreeReturnable {

	// Returns the type that the liter returns
	public Type getType();

	public String getValue();

}
