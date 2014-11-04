package wacc.slack.AST.statements;


// Class which represents a 'begin' expr 'end' scope

public class BeginEndAST extends StatAST {

	private final StatAST body;

	public BeginEndAST(StatAST body, int linePos, int charPos) {
		super(linePos, charPos);
		addStat(this);
		this.body = body;
	}
	
	public StatAST getBody() {
		return body;
	}

}
