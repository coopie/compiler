package wacc.slack.AST.Expr;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class UnaryExprAST implements ExprAST {

	private final UnaryOp unaryOp;
	private final ExprAST expr;
	private final int linePos, charPos;
	
	public UnaryExprAST(UnaryOp unaryOp, ExprAST expr, int linePos, int charPos) {
		this.unaryOp = unaryOp;
		this.expr = expr;
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

	@Override
	public void accept(ASTVisitor visitor) {
		expr.accept(visitor);
		visitor.visit(this);
	}
	
	@Override
	public Type getType() {
		return unaryOp.getType();
	}
	
	@Override
	public void checkTypes() {
		if (!unaryOp.getType().equals(expr.getType())) {
			ErrorRecords.getInstance().record(new ErrorRecord() {

				@Override
				public String getMessage() {
					return "Expected types do not check.";
				}

				@Override
				public int getLineNumber() {
					return getLine();
				} 
			});
		}
	}

	public UnaryOp getUnaryOp() {
		return unaryOp;
	}

	public ExprAST getExpr() {
		return expr;
	}
}
