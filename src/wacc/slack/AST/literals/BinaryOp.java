package wacc.slack.AST.literals;

import wacc.slack.AST.types.Type;

public enum BinaryOp implements Liter {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

	public Type getType() {
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
