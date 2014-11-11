package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.Assignable;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.expectations.FunctionReturnTypeExpectation;

public class AssignStatAST extends StatAST implements WaccAST {
	private final Assignable lhs;
	private final AssignRHS rhs;
	public AssignStatAST(Assignable lhs, AssignRHS rhs, FilePosition filePos) {
		super(filePos);
		this.lhs = lhs;
		this.rhs = rhs;
		
		if(rhs instanceof CallAST) {
			ErrorRecords.getInstance().addExpectation(new FunctionReturnTypeExpectation(((CallAST) rhs).getIdent(),lhs.getType(),filePos));
		}
	}

	public Assignable getLhs() {
		return lhs;
	}

	public AssignRHS getRhs() {
		return rhs;
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(lhs,rhs));
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
