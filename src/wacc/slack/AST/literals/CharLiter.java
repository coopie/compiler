package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class CharLiter implements Liter {

	public CharLiter(String text) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getType() {
		return BaseType.T_char;
	}

}
