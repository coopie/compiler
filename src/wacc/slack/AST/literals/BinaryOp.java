package wacc.slack.AST.literals;

import wacc.slack.AST.types.Type;

public enum BinaryOp implements Liter {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

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
