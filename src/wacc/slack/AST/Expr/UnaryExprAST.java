package wacc.slack.AST.Expr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class UnaryExprAST implements ExprAST {

	private final UnaryOp unaryOp;
	private final ExprAST expr;
	private final FilePosition filePos;
	private final boolean correctSubExpresions;
	private final ErrorRecords errors = ErrorRecords.getInstance();
	

	public UnaryExprAST(UnaryOp unOp, ExprAST expr, final FilePosition filePos) {
		this.unaryOp = unOp;
		this.expr = expr;
		this.filePos = filePos;
		
		correctSubExpresions = checkTypes();	
		
		if(!correctSubExpresions) {
			errors.record(new TypeMismatchError(filePos, expr.getType(), unaryOp.getType()));
		}	
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public Type getType() {
		return unaryOp.getType();	
	}
	
	private boolean checkTypes() {
		switch(unaryOp) {
			case NOT: return expr.getType() == BaseType.T_bool; 
			case MINUS: return expr.getType() == BaseType.T_int; 
			case LEN: return expr.getType().equals(new WaccArrayType(BaseType.T_int)); 
			case ORD: return expr.getType() == BaseType.T_char; 
			case CHR: return expr.getType() == BaseType.T_int; 
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
		return errors;
	}
	
	@Override
	public String toString() {
		return unaryOp.toString();
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}

}
