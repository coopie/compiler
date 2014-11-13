package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.assignables.Assignable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class ReadStatementAST extends StatAST{
	
	private final Assignable assignable;

	public ReadStatementAST(Assignable assignable, FilePosition filePos) {
		super(filePos);
		this.assignable = assignable;
		if (!((assignable.getType() == BaseType.T_int) || 
			  (assignable.getType() == BaseType.T_char))) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, assignable.getType(), BaseType.T_int, BaseType.T_char));
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
