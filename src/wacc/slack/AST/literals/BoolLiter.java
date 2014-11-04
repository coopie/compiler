package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class BoolLiter implements Liter {

	private final String value;
	private final int linePos, charPos;
	
	public BoolLiter(String value, int linePos, int charPos) {
		this.value = value;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public Type getType() {
		return BaseType.T_bool;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
}
