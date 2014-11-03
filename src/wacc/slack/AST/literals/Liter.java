package wacc.slack.AST.literals;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public interface Liter extends ParseTreeReturnable {

	// returns the type that the liter returns
	public Type getType();
	public String getValue();

}
