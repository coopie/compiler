package wacc.slack.AST.Expr;

import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ValueExprAST implements ExprAST {

	private final Liter liter;
	
	public ValueExprAST(Liter liter) {
		this.liter = liter;
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Type getType() {
		return liter.getType();
	}
	
	@Override
	public void checkTypes() {
		// Values never have type errors
	}
}
