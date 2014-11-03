package wacc.slack.AST;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.visitors.ASTVisitor;

public class SndAST implements Assignable {
	
	private final ExprAST expr;
	
	public SndAST(ExprAST expr) {
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
					return getPosition();
				} 
			});
		}
	}
	
	public ExprAST getExpr() {
		return expr;
	}
}
