package wacc.slack.AST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import antlr.WaccParser.StatContext;

public class StatAST implements WaccAST,Iterable<StatAST> {

	private final List<StatAST> stats = new LinkedList<StatAST>();
	
	public StatAST(StatContext ctx) {
		ASTBuilder ast = new ASTBuilder();
		
		//checks if there is a list of stats
		if(ctx.stat() != null) {
			for(StatContext s : ctx.stat()) {
				stats.add(ast.visitStat(s));
			}
		}else {
			stats.add(this);
		}
		
		//TODO: implement other stat stuff
	}

	@Override
	public Iterator<StatAST> iterator() {
		return stats.iterator();
	}

}
