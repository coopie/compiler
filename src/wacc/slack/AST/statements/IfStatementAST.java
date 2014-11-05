package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class IfStatementAST extends StatAST implements WaccAST {

	private final StatAST falseStats, trueStats;
	private final ExprAST cond;
	public IfStatementAST(ExprAST exprAST, StatAST trueStats,
			StatAST falseStats, FilePosition filePos) {
		super(filePos);
		this.cond = exprAST;
		this.trueStats = trueStats;
		this.falseStats = falseStats;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
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
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(cond, trueStats, falseStats));
	}

	@Override
	public String toString() {
		return "if";
	}
}
