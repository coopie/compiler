package wacc.slack.AST.statements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class StatAST implements WaccAST, Iterable<StatAST> {

	private final List<StatAST> stats;
	private final FilePosition filePos;

	public StatAST(FilePosition filePos) {
		stats = new LinkedList<>();
		this.filePos = filePos;
	}
	
	public StatAST(List<StatAST> stats, FilePosition filePos) {
		this.stats = stats;
		this.filePos = filePos;
	}
	
	protected void addStat(StatAST stat) {
		if(stats.size() < 1) {
			stats.add(stat);
		} else {
			throw new RuntimeException("only one stat allowed");
		}
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public Iterator<StatAST> iterator() {
		return stats.iterator();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		for(StatAST s : this) {
			s.accept(visitor);
		}
	}

	@Override
	public List<WaccAST> getChildren() {
		List<WaccAST> l = new LinkedList<WaccAST>();
		l.addAll(stats);
		return l;
	}
}
