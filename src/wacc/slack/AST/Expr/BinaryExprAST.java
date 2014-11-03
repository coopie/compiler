package wacc.slack.AST.Expr;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
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
	
	@Override
	public void checkTypes() {
		if (!binaryOp.getType().equals(exprL.getType()) || 
				!exprL.getType().equals(exprR.getType())) {
			ErrorRecords.getInstance().record(new ErrorRecord() {

				@Override
				public String getMessage() {
					return "Expected types do not check.";
				}

				@Override
				public int getLineNumber() {
					return getPosition();
				} 
			});
		}
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
