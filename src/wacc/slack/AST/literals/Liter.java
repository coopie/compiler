package wacc.slack.AST.literals;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.types.Type;

public interface Liter extends ParseTreeReturnable {

	public Type getType();
}
