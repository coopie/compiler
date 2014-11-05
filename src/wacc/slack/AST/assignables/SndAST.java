package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.visitors.ASTVisitor;

public class SndAST implements Assignable {
	
	private final ExprAST expr;
	private final FilePosition filePos;
	
	public SndAST(ExprAST expr, FilePosition filePos) {
		this.expr = expr;
		this.filePos = filePos;
		
		checkType();
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public void accept(ASTVisitor<?> visitor) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "snd";
	}
	
	private void checkType() {
		if (expr instanceof PairType) {
			ErrorRecords.getInstance().record(new ErrorRecord() {

				@Override
				public String getMessage() {
					return "Expected types do not check.";
				}

				@Override
				public FilePosition getFilePosition() {
					return filePos;
				} 
			});
		}
	}
	
	public ExprAST getExpr() {
		return expr;
	}
	
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}
}
