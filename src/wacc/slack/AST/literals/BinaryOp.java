package wacc.slack.AST.literals;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.types.Type;

public enum BinaryOp implements ParseTreeReturnable {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

	public Type getType() {
		return null;
	}
	
}
