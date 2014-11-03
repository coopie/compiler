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
	private final int linePos, charPos;
	
	public ArrayElem(String ident, ExprAST expr, int linePos, int charPos) {
		this.ident = ident;
		this.expr = expr;
		this.linePos = linePos;
		this.charPos = charPos;

		checkType();
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
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return ident;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
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
					return getLine();
				} 
			});
		}
	}
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getIdent() {
		return ident;
	}
	
	public ExprAST getExpr() {
		return expr;
	}
}
