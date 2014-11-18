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
import wacc.slack.AST.statements.ExitStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;
/**
 * 
 * 
 * 
 */
public abstract class PartialASTVisitor<T> implements ASTVisitor<T> {
	
	private final String errorMsg;

	public PartialASTVisitor(String errorMsg) {
		this.errorMsg = errorMsg;	
	}
	
	public PartialASTVisitor() {
		this.errorMsg = "node shouldn't be encountered";	
	}

	@Override
	public T visit(FuncAST func) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ProgramAST prog) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(StatListAST statAST) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(AssignStatAST assignStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(BeginEndAST beginEnd) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(IfStatementAST ifStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(SkipStatementAST skipStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(WhileStatementAST whileStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ReturnStatementAST exprStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(PrintlnStatementAST printlnStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(PrintStatementAST printStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ReadStatementAST readStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ExitStatementAST exitStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(FreeStatementAST freeStat) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ArrayElemAST arrayElem) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(FstAST fst) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(SndAST snd) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ArrayLiterAST arrayLiter) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(CallAST call) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(NewPairAST newPair) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(BinaryExprAST binExpr) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(UnaryExprAST unExpr) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(ValueExprAST valueExpr) {
		throw new RuntimeException(errorMsg);
	}

	@Override
	public T visit(VariableAST variable) {
		throw new RuntimeException(errorMsg);
	}

}
