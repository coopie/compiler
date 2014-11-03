package wacc.slack.AST.Expr;

import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ValueExprAST implements ExprAST {

	private final Liter liter;
	
	public ValueExprAST(Liter l) {
		this.liter = l ;
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
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
	
	public void checkTypes() {
		// Values never have type errors

	}
}
