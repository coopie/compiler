package wacc.slack.AST;

import wacc.slack.AST.literals.BinaryOpAST;
import wacc.slack.AST.literals.LiterAST;
import wacc.slack.AST.literals.UnaryOpAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ExprAST implements WaccAST{

	private final LiterAST literAST;
	private final ExprAST expr;
	private final Type type;
	
	public ExprAST(LiterAST literAST) {
		this.literAST = literAST;
		this.expr = null;
		this.type = literAST.getType();
	}

	public ExprAST(ExprAST expr, UnaryOpAST unaryOp) {
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
			BinaryOpAST binaryOp) {
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

	public LiterAST getLiterAST() {
		return literAST;
	}

	public ExprAST getExpr() {
		return expr;
	}

	public Type getType() {
		return type;
	}
}
