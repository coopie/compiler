package wacc.slack.AST.literals;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public enum BinaryOpAST implements WaccAST {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public Type getType() {
		return null;
	}
	
}
