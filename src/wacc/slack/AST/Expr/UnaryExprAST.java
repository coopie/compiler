package wacc.slack.AST.Expr;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class UnaryExprAST implements ExprAST {

	private final UnaryOp unaryOp;
	private final ExprAST expr;
	private final int linePos, charPos;
	private final boolean correctSubExpresions;
	private final ErrorRecords error = ErrorRecords.getInstance();
	

	public UnaryExprAST(UnaryOp unOp, ExprAST expr, int linePos, int charPos) {
		this.unaryOp = unOp;
		this.expr = expr;
		this.linePos = linePos;
		this.charPos = charPos;
		correctSubExpresions = checkTypes();
		if(!correctSubExpresions) {
			error.record(new ErrorRecord(){

				@Override
				public String getMessage() {
					return "type mismatch for " + unaryOp.toString() ;
				}

				@Override
				public int getLineNumber() {
					// TODO Auto-generated method stub
					return 0;
				}
				
			});
		}
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
	
	private boolean checkTypes() {
		switch(unaryOp) {
			case NOT: return expr.getType() == BaseType.T_bool; 
			case MINUS: return expr.getType() == BaseType.T_int; 
			case LEN: return expr.getType() == BaseType.T_int; 
			case ORD: return expr.getType() == BaseType.T_int; 
			case CHR: return expr.getType() == BaseType.T_char; 
			default: throw new RuntimeException("not suppoerted UnaryOP");
		}
	}
	
	public boolean isCorrectSubExpresions() {
		return correctSubExpresions;
	}

	public UnaryOp getUnaryOp() {
		return unaryOp;
	}

	public ExprAST getExpr() {
		return expr;
	}

	public ErrorRecords getError() {
		return error;
	}
}
