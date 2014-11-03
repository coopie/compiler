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
	private final boolean correctSubExpresions;
	private ErrorRecords error  = ErrorRecords.getInstance();
	
	public UnaryExprAST(UnaryOp unaryOp, ExprAST expr) {
		this.unaryOp = unaryOp;
		this.expr = expr;
		correctSubExpresions = checkTypes();
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
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

	public UnaryOp getUnaryOp() {
		return unaryOp;
	}

	public ExprAST getExpr() {
		return expr;
	}
}
