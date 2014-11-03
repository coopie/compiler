package wacc.slack.AST;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.visitors.ASTVisitor;

public class SndAST implements Assignable {
	
	private final ExprAST expr;
	private final int linePos, charPos;
	
	public SndAST(ExprAST expr, int linePos, int charPos) {
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
		return "snd";
	}
	
	@Override
	public void checkType() {
		if (expr instanceof PairType) {
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
	
	public ExprAST getExpr() {
		return expr;
	}
}
