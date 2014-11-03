package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class BoolLiter implements Liter {

	private final boolean b;
	
	public BoolLiter(boolean b) {
		this.b = b;
	}

	public boolean getBool() {
		return b;
	}

	@Override
	public Type getType() {
		return BaseType.T_bool;
	}

}
