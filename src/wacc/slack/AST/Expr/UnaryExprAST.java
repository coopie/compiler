package wacc.slack.AST.Expr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class UnaryExprAST implements ExprAST {

	private final UnaryOp unaryOp;
	private final ExprAST expr;
	private final FilePosition filePos;
	private final boolean correctSubExpresions;
	private final ErrorRecords error = ErrorRecords.getInstance();
	

	public UnaryExprAST(UnaryOp unOp, ExprAST expr, final FilePosition filePos) {
		this.unaryOp = unOp;
		this.expr = expr;
		this.filePos = filePos;
		correctSubExpresions = checkTypes();
		if(!correctSubExpresions) {
			error.record(new ErrorRecord(){

				@Override
				public String getMessage() {
					return "type mismatch for " + unaryOp.toString() ;
				}

				@Override
				public FilePosition getFilePosition() {
					return filePos;
				}
				
			});
		}
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
		expr.accept(visitor);
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
	
	@Override
	public String toString() {
		return unaryOp.toString() + " " + expr.toString();
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}
}
