package wacc.slack.AST.statements;

import wacc.slack.AST.StatAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class SkipStatementAST extends StatAST {

	public SkipStatementAST() {
		addStat(this);
	}
	@Override
	public String toString() {
		return "skip";
	}
	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}