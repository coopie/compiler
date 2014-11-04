package wacc.slack.AST.statements;

import wacc.slack.FilePosition;

// Class which represents a 'begin' expr 'end' scope

public class BeginEndAST extends StatAST {

	private final StatAST body;

	public BeginEndAST(StatAST body, FilePosition filePos) {
		super(filePos);
		addStat(this);
		this.body = body;
	}

	public StatAST getBody() {
		return body;
	}

}
