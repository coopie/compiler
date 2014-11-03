package wacc.slack.AST;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayElem implements Assignable, Liter {

	private final String ident;
	private final ExprAST expr;
	
	public ArrayElem(String ident, ExprAST expr) {
		this.ident = ident;
		this.expr = expr;

		checkType();
	}

	@Override
	public int getPosition() {
		return 0;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
	}

	@Override
	public String getName() {
		return ident;
	}

	@Override
	public Type getType() {
		// NEEDS TO BE IMPLEMENTED ONCE IDENT IS CREATED
		return null;
	}
	
	@Override
	public void checkType() {
		if (!expr.getType().equals(BaseType.T_int)) {
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
	
	public String getIdent() {
		return ident;
	}
	
	public ExprAST getExpr() {
		return expr;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
