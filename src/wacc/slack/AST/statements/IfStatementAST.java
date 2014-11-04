package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class IfStatementAST extends StatAST {

	private final StatAST falseStats, trueStats;
	private final ExprAST cond;

	public IfStatementAST(ExprAST exprAST, StatAST trueStats,
			StatAST falseStats, FilePosition filePos) {
		super(filePos);
		addStat(this);
		this.cond = exprAST;
		this.trueStats = trueStats;
		this.falseStats = falseStats;
	}

	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
	
	public StatAST getFalseStats() {
		return falseStats;
	}
	
	public ExprAST getCond() {
		return cond;
	}
	
	public StatAST getTrueStats() {
		return trueStats;
	}
	
	@Override
	public String toString() {
		String trueBody = "";
		String falseBody = "";
		
		for(StatAST st : trueStats) {
			trueBody += " " + st.toString();
		}
		
		for(StatAST st : falseStats) {
			falseBody += " " + st.toString();
		}
		
		return "if " + cond.toString() + " then " + trueBody + " else " + falseBody;
	}
}
