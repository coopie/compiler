package wacc.slack.AST.statements;

import wacc.slack.AST.StatAST;

public class ScopeAST extends StatAST {

	private final StatAST body;

	public ScopeAST(StatAST body) {
		this.body = body;
	}

}
