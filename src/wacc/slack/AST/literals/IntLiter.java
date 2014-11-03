package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class IntLiter implements Liter {

	private final int i, linePos, charPos;
	
	public IntLiter(int i, int linePos, int charPos) {
		this.i = i;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public Type getType() {
		return BaseType.T_int;
	}
	
	@Override
	public String toString() {
		return "" + i;
	}

	@Override
	public String getValue() {
		return Integer.toString(i);
	}
	
	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	public int getInt() {
		return i;
	}

}
