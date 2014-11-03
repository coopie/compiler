package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class StringLiter implements Liter {

	public StringLiter(String text) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getType() {
		return BaseType.T_string;
	}

}
