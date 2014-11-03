package wacc.slack.AST.Expr;

import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ValueExprAST implements ExprAST {

	private final Liter liter;
	private final int linePos, charPos;
	
	public ValueExprAST(Liter l, int linePos, int charPos) {
		this.liter = l ;
		this.linePos = linePos;
		this.charPos = charPos;
	}
	
	@Override
	public int getLine() {
		return linePos;
	}
	
	@Override
	public int getCharColumn() {
		return charPos;
	}

	public String getValue() {
		return liter.getValue();
	}
	
	@Override
	public Type getType() {
		return liter.getType();
	}
	
	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);	
	}

	@Override
	public void checkTypes() {
	}
}
