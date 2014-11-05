package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

// Class which represents a 'begin' expr 'end' scope

public class BeginEndAST extends StatAST implements WaccAST {

	private final StatAST body;
	public BeginEndAST(StatAST body, FilePosition filePos) {
		super(filePos);
		this.body = body;
	}

	public StatAST getBody() {
		return body;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(body));
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
