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

public class FstAST implements Assignable {
	
	private final ExprAST expr;
	private final FilePosition filePos;
	
	public FstAST(ExprAST expr, FilePosition filePos) {
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
		return "fst";
	}
	
	private void checkType() {
		if (!(expr.getType() instanceof PairType)) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(BaseType.T_pair, expr.getType(), filePos));
		}
	}
	
	public ExprAST getExpr() {
		return expr;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}

	@Override
	public Type getType() {
		return ((PairType)expr.getType()).getFst();
	}
}
