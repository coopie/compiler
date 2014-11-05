package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;

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

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(body));
	}
}
