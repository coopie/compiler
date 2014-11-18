package wacc.slack.AST.visitors;

import java.util.LinkedList;
import java.util.List;

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
import wacc.slack.instructions.PseudoInstruction;

// NB: use LinkedList 

public class IntermediateCodeGenerator implements
		ASTVisitor<List<PseudoInstruction>> {

	@Override
	public List<PseudoInstruction> visit(FuncAST func) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ProgramAST prog) {
		
		List<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		
		// TODO: functions, exit codes maybe

		// --- implementation of the "main" function, i.e. the stats after the
		// function definitions
		
		// this is not 
		instrList.addAll(prog.getStatements().accept(this));

		return prog.getStatements().accept(this);
	}

	@Override
	public List<PseudoInstruction> visit(StatListAST statAST) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(AssignStatAST assignStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(BeginEndAST beginEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(IfStatementAST ifStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(SkipStatementAST skipStat) {
		return new LinkedList<PseudoInstruction>();
	}

	@Override
	public List<PseudoInstruction> visit(WhileStatementAST whileStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ReturnStatementAST exprStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(PrintlnStatementAST printlnStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(PrintStatementAST printStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ReadStatementAST readStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ExitStatementAST exitStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(FreeStatementAST freeStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ArrayElemAST arrayElem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(FstAST fst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(SndAST snd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ArrayLiterAST arrayLiter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(CallAST call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(NewPairAST newPair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(BinaryExprAST binExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(UnaryExprAST unExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(ValueExprAST valueExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PseudoInstruction> visit(VariableAST variable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<PseudoInstruction> attachLabelToFirstElement(String name,
										List<PseudoInstruction> instrList) {
		return null;
	}

}
