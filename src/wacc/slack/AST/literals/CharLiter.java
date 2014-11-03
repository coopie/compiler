package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class CharLiter implements Liter {

	private final String text;

	public CharLiter(String text) {
		this.text = text;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getType() {
		return BaseType.T_char;
	}

	@Override
	public String getValue() {
		return text;
	}
	
	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

}
