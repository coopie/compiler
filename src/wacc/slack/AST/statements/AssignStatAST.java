package wacc.slack.AST.statements;

import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.Assignable;


public class AssignStatAST extends StatAST {
	private final Assignable lhs;
	private final AssignRHS rhs;

	public AssignStatAST(Assignable lhs, AssignRHS rhs, int linePos, int charPos) {
		super(linePos, charPos);
		this.lhs = lhs;
		this.rhs = rhs;
		addStat(this);
		
	}
}
