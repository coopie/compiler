package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class BoolLiter implements Liter {

	private final boolean b;
	private final int linePos, charPos;
	
	public BoolLiter(boolean b, int linePos, int charPos) {
		this.b = b;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public Type getType() {
		return BaseType.T_bool;
	}

	@Override
	public String getValue() {
		return Boolean.toString(b);
	}
	
	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}

	public boolean getBool() {
		return b;
	}
}
