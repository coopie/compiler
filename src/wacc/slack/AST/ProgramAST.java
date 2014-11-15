package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ProgramAST implements WaccAST {

	private final List<FuncAST> func;
	private final StatAST stat;
	private final FilePosition filePos;

	public ProgramAST(List<FuncAST> func, StatAST stat, FilePosition filePos) {
		this.func = func;
		this.stat = stat;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public List<FuncAST> getFunctions() {
		return func;
	}

	public StatAST getStatements() {
		return stat;
	}

	@Override
	public List<WaccAST> getChildren() {
		List<WaccAST> l = new LinkedList<WaccAST>();
		l.add(stat);
		l.addAll(func);
		return l;
	}
}
