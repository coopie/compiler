package wacc.slack.AST.statements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class StatAST implements WaccAST, Iterable<StatAST> {

	private final List<StatAST> stats;
	private final int linePos, charPos;

	public StatAST(int linePos, int charPos) {
		stats = new LinkedList<>();
		this.linePos = linePos;
		this.charPos = charPos;
	}
	
	public StatAST(List<StatAST> stats, int linePos, int charPos) {
		this.stats = stats;
		this.linePos = linePos;
		this.charPos = charPos;
	}
	
	protected void addStat(StatAST stat) {
		if(stats.size() < 1) {
			stats.add(stat);
		} else {
			throw new RuntimeException("only one stat allowed");
		}
	}

	@Override
	public Iterator<StatAST> iterator() {
		return stats.iterator();
	}

	@Override
	public int getLine() {
		return linePos;
	}
	
	@Override
	public int getCharColumn() {
		return charPos;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		for(StatAST s : this) {
			s.accept(visitor);
		}
	}

}
