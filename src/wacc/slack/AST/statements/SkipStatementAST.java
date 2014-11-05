package wacc.slack.AST.statements;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class SkipStatementAST extends StatAST {

	public SkipStatementAST(FilePosition filePos) {
		super(filePos);
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

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>();
	}
}