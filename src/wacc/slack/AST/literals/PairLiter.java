package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class PairLiter implements Liter {

	private final int linePos, charPos;
	
	public PairLiter(int linePos, int charPos) {
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public Type getType() {
		return BaseType.T_pair;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
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
