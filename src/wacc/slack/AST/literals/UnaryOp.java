package wacc.slack.AST.literals;

import wacc.slack.AST.types.Type;

public enum UnaryOp implements Liter {
	NOT, MINUS, LEN, ORD, CHR ;

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
	
	public Type getType() {
		return null;
	}
}
