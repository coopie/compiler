package wacc.slack.AST.Expr;

import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class BinaryExprAST implements ExprAST {

	private final BinaryOp binaryOp;
	private final ExprAST exprL, exprR;
	
	public BinaryExprAST(BinaryOp binaryOp, ExprAST exprL, ExprAST exprR) {
		this.binaryOp = binaryOp;
		this.exprL = exprL;
		this.exprR = exprR;
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		exprL.accept(visitor);
		exprR.accept(visitor);
		visitor.visit(this);
	}
	
	@Override
	public Type getType() {
		return binaryOp.getType();
	}
	

	private boolean checkTypes() {
		return true;
	}

	public BinaryOp getBinaryOp() {
		return binaryOp;
	}
	
	public ExprAST getExprL() {
		return exprL;
	}
	
	public ExprAST getExprR() {
		return exprR;
	}
}
