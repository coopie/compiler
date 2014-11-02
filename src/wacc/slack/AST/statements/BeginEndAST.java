package wacc.slack.AST.statements;

import wacc.slack.AST.StatAST;

// Class which represents a 'begin' expr 'end' scope

public class BeginEndAST extends StatAST {

	private final StatAST body;

	public BeginEndAST(StatAST body) {
		this.body = body;
	}

}
