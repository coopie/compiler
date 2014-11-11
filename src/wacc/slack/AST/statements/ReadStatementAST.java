package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.assignables.ArrayElemAST;
import wacc.slack.AST.assignables.Assignable;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.SndAST;
import wacc.slack.AST.assignables.VariableAST;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.IllegalOperationError;

public class ReadStatementAST extends StatAST{
	
	private final Assignable assignable;

	public ReadStatementAST(Assignable assignable, FilePosition filePos) {
		super(filePos);
		this.assignable = assignable;
		if (!(assignable instanceof ArrayElemAST) && 
			!(assignable instanceof FstAST) &&
			!(assignable instanceof SndAST) &&
			!(assignable instanceof VariableAST)) {
			ErrorRecords.getInstance().record(
					new IllegalOperationError(filePos));
		}
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(assignable));
	}

	public Assignable getAssignable() {
		return assignable;
	}

}
