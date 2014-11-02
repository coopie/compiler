package wacc.slack.AST.statements;

import wacc.slack.AST.StatAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ScopeAST extends StatAST {

	private final StatAST body;

	public ScopeAST(StatAST body) {
		this.body = body;
	}
	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
