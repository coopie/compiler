package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class PairLiter implements Liter {


	@Override
	public Type getType() {
		return BaseType.T_pair;
	}
}
