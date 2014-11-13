package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArrayElemAST;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.assignables.NewPairAST;
import wacc.slack.AST.assignables.SndAST;
import wacc.slack.AST.assignables.VariableAST;
import wacc.slack.AST.literals.ArrayLiterAST;
import wacc.slack.AST.statements.AssignStatAST;
import wacc.slack.AST.statements.BeginEndAST;
import wacc.slack.AST.statements.ExprStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;

public class CheckReturnVisitor implements ASTVisitor<Boolean> {

	private Boolean isReturnStat(StatAST statAST) {
		return statAST instanceof ReturnStatementAST;
	}

	@Override
	public Boolean visit(FuncAST func) {
		return func.getStat().accept(this);
	}

	@Override
	public Boolean visit(ProgramAST prog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(StatListAST statAST) {
		Boolean containsReturn = false;

		if (isReturnStat(statAST)) {
			return true;
		}
		
		for (StatAST stat : statAST) {
			containsReturn |= stat.accept(this);
		}
		return containsReturn;
	}

	@Override
	public Boolean visit(AssignStatAST assignStat) {
		return false;
	}

	@Override
	public Boolean visit(BeginEndAST beginEnd) {
		return beginEnd.getBody().accept(this);
	}

	@Override
	public Boolean visit(IfStatementAST ifStat) {
		// There must be a return statement in both the if and else branches
		return ifStat.getTrueStats().accept(this)
				&& ifStat.getFalseStats().accept(this);
	}

	@Override
	public Boolean visit(SkipStatementAST skipStat) {
		return false;
	}

	@Override
	public Boolean visit(WhileStatementAST whileStat) {
		return false;
	}

	@Override
	public Boolean visit(ExprStatementAST exprStat) {
		return false;
	}

	@Override
	public Boolean visit(ReadStatementAST readStatementAST) {
		return false;
	}

	@Override
	public Boolean visit(ArrayElemAST arrayElem) {
		return false;
	}

	@Override
	public Boolean visit(FstAST fst) {
		return false;
	}

	@Override
	public Boolean visit(SndAST snd) {
		return false;
	}

	@Override
	public Boolean visit(ArrayLiterAST arrayLiter) {
		return false;
	}

	@Override
	public Boolean visit(CallAST call) {
		return false;
	}

	@Override
	public Boolean visit(NewPairAST newPair) {
		return false;
	}

	@Override
	public Boolean visit(BinaryExprAST binExpr) {
		return false;
	}

	@Override
	public Boolean visit(UnaryExprAST unExpr) {
		return false;
	}

	@Override
	public Boolean visit(ValueExprAST valueExpr) {
		return false;
	}

	@Override
	public Boolean visit(VariableAST variable) {
		return false;
	}

}
