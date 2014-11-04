package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class CharLiter implements Liter {

	private final String text;

	public CharLiter(String text) {
		this.text = text;
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
	public FilePosition getFilePosition() {
		return null;
	}
}
