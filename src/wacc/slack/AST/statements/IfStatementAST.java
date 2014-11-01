package wacc.slack.AST.statements;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.StatAST;

public class IfStatementAST extends StatAST {

	private final StatAST falseStats;
	private final ExprAST cond;
	private final StatAST trueStats;

	public IfStatementAST(ExprAST cond, StatAST trueStats,
			StatAST falseStats) {
				addStat(this);
				this.cond = cond;
				this.trueStats = trueStats;
				this.falseStats = falseStats;
	}

}
