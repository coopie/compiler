package wacc.slack.AST.literals;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public enum BinaryOpAST implements WaccAST {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
