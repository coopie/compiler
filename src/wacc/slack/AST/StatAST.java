package wacc.slack.AST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class StatAST implements WaccAST,Iterable<StatAST> {

	private final List<StatAST> stats;

	public StatAST() {
		stats = new LinkedList<>();
	}
	
	public StatAST(List<StatAST> stats) {
		this.stats = stats;
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
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

}
