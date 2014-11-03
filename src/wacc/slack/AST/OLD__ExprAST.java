package wacc.slack.AST;

import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class OLD__ExprAST implements WaccAST{

	private final Liter literAST;
	private final OLD__ExprAST expr;
	private final Type type;
	
	public OLD__ExprAST(Liter literAST) {
		this.literAST = literAST;
		this.expr = null;
		this.type = literAST.getType();
	}

	public OLD__ExprAST(OLD__ExprAST expr, UnaryOp unaryOp) {
		this.literAST = null;
		this.expr = null;
		
		if (expr.getType() == unaryOp.getType()) {
			this.type = expr.getType();
		} else {
			// Report error
			this.type = null;
		}
	}
	
	public OLD__ExprAST(OLD__ExprAST expr, OLD__ExprAST expr2,
			BinaryOp binaryOp) {
		this.literAST = null;
		this.expr = null;
		
		if (expr.getType() == expr2.getType() && expr.getType() == binaryOp.getType()) {
			this.type = expr.getType();
		} else {
			// Report error
			this.type = null;
		}
	}

	public OLD__ExprAST(OLD__ExprAST expr) {
		this.literAST = null;
		this.expr = expr;
		this.type = expr.type;
	}
	
	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		//visitor.visdit(this);
	}

	public Liter getLiterAST() {
		return literAST;
	}

	public OLD__ExprAST getExpr() {
		return expr;
	}

	public Type getType() {
		return type;
	}
}
