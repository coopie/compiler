package wacc.slack.AST.statements;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class SkipStatementAST extends StatAST implements WaccAST {

	public SkipStatementAST(FilePosition filePos) {
		super(filePos);
	}

	@Override
	public String toString() {
		return "skip";
	}
	@Override 
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>();
	}
}