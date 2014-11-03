package wacc.slack.AST.literals;

import wacc.slack.AST.types.Type;

public enum UnaryOp implements Liter {
	NOT, MINUS, LEN, ORD, CHR ;
	
	public Type getType() {
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
