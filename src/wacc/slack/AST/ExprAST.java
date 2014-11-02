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
		this.type = getTypeOfLiterAST(literAST);
	}

	public ExprAST(ExprAST expr, UnaryOpAST unaryOp) {
		// TODO Auto-generated constructor stub
		this.literAST = null;
		this.expr = null;
		
		// Need to test this
		if (expr.getType() == unaryOp.getType()) {
			this.type = expr.getType();
		} else {
			// Report error
			this.type = null;
		}
	}
	
	public ExprAST(ExprAST expr, ExprAST expr2,
			BinaryOpAST binaryOp) {
		// TODO Auto-generated constructor stub
		this.literAST = null;
		this.expr = null;
		
		// Need to test this
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
	
	private Type getTypeOfLiterAST(LiterAST literAST) {
		return null;
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
