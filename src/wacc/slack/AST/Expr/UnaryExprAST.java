package wacc.slack.AST.Expr;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class UnaryExprAST implements ExprAST {

	private final UnaryOp unaryOp;
	private final ExprAST expr;
	
	public UnaryExprAST(UnaryOp unaryOp, ExprAST expr) {
		this.unaryOp = unaryOp;
		this.expr = expr;
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		expr.accept(visitor);
		visitor.visit(this);
	}
	
	@Override
	public Type getType() {
		return unaryOp.getType();
	}

	public UnaryOp getUnaryOp() {
		return unaryOp;
	}

	public ExprAST getExpr() {
		return expr;
	}
}
