package wacc.slack.AST;

import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ExprAST implements WaccAST{

	private final Liter literAST;
	private final ExprAST expr;
	private final Type type;
	
	public ExprAST(Liter literAST) {
		this.literAST = literAST;
		this.expr = null;
		this.type = literAST.getType();
	}

	public ExprAST(ExprAST expr, UnaryOp unaryOp) {
		this.literAST = null;
		this.expr = null;
		
		if (expr.getType() == unaryOp.getType()) {
			this.type = expr.getType();
		} else {
			// Report error
			this.type = null;
		}
	}
	
	public ExprAST(ExprAST expr, ExprAST expr2,
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

	public ExprAST(ExprAST expr) {
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
		visitor.visit(this);
	}

	public Liter getLiterAST() {
		return literAST;
	}

	public ExprAST getExpr() {
		return expr;
	}

	public Type getType() {
		return type;
	}
}
