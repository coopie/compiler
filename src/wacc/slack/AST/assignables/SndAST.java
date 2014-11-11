package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecord;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class SndAST implements Assignable {
	
	private final Assignable expr;
	private final FilePosition filePos;
	
	public SndAST(Assignable expr, FilePosition filePos) {
		this.expr = expr;
		this.filePos = filePos;
		
		checkType();
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
	public String getName() {
		return expr.getName();
	}
	
	private void checkType() {
		if (!(expr instanceof PairType)) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, expr.getType(), BaseType.T_pair));
		}
	}
	
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}
	
	@Override
	public Type getType() {
		return ((PairType)expr.getType()).getSnd();
	}
}
