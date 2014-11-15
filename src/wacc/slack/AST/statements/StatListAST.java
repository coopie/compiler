package wacc.slack.AST.statements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public final class StatListAST extends StatAST implements Iterable<StatAST> {

	private final List<StatAST> stats;

	public StatListAST(FilePosition filePos) {
		super(filePos);
		stats = new LinkedList<>();
	}

	public StatListAST(List<StatAST> stats, FilePosition filePos) {
		super(filePos);
		this.stats = stats;
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public List<WaccAST> getChildren() {
		List<WaccAST> l = new LinkedList<WaccAST>();
		l.addAll(stats);
		return l;
	}

	@Override
	public Iterator<StatAST> iterator() {
		return stats.iterator();
	}
}
