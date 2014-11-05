package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.Assignable;

public class AssignStatAST extends StatAST {
	private final Assignable lhs;
	private final AssignRHS rhs;

	public AssignStatAST(Assignable lhs, AssignRHS rhs, FilePosition filePos) {
		super(filePos);
		this.lhs = lhs;
		this.rhs = rhs;
		addStat(this);
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
}
