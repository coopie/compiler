package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class IntLiter implements Liter {

	private final int i;
	
	public IntLiter(int i) {
		this.i = i;
	}

	public int getInt() {
		return i;
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
		// TODO Auto-generated method stub
		return Integer.toString(i);
	}

}