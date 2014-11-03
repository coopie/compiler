package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public enum UnaryOp implements Liter {
	NOT, MINUS, LEN, ORD, CHR ;
	
	public Type getType() {
		switch(this) {
			case NOT: return BaseType.T_bool;
			case MINUS: return BaseType.T_int;
			case LEN: return BaseType.T_int;
			case ORD: return BaseType.T_int;
			case CHR: return BaseType.T_char;
			default: throw new RuntimeException("not supported UnaryOP");
		}
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
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
