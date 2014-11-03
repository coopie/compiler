package wacc.slack.AST.literals;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.types.Type;

public enum UnaryOp implements ParseTreeReturnable {
	NOT, MINUS, LEN, ORD, CHR ;
	
	public Type getType() {
		return null;
	}
	
}
