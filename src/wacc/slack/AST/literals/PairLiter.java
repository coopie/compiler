package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class PairLiter implements Liter {


	@Override
	public Type getType() {
		return BaseType.T_pair;
	}
}
