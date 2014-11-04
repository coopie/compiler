package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class StringLiter implements Liter {

	private final String text;
	
	public StringLiter(String text) {
		this.text = text;
	}

	@Override
	public Type getType() {
		return BaseType.T_string;
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
