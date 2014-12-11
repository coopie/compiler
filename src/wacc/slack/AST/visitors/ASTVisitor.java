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
import wacc.slack.AST.statements.IfWithoutElseStatement;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;

public interface ASTVisitor<T> {
	T visit(FuncAST func);

	T visit(ProgramAST prog);

	// Statements
	T visit(StatListAST statAST);

	T visit(AssignStatAST assignStat);

	T visit(BeginEndAST beginEnd);

	T visit(IfStatementAST ifStat);
	
	T visit(IfWithoutElseStatement ifWithoutElseStat);

	T visit(SkipStatementAST skipStat);

	T visit(WhileStatementAST whileStat);

	T visit(ReturnStatementAST exprStat);

	T visit(PrintlnStatementAST printlnStat);

	T visit(PrintStatementAST printStat);

	T visit(ReadStatementAST readStat);

	T visit(ExitStatementAST exitStat);

	T visit(FreeStatementAST freeStat);

	// Assignables
	T visit(ArrayElemAST arrayElem);

	T visit(FstAST fst);

	T visit(SndAST snd);

	T visit(ArrayLiterAST arrayLiter);

	T visit(CallAST call);

	T visit(NewPairAST newPair);

	// Expressions
	T visit(BinaryExprAST binExpr);

	T visit(UnaryExprAST unExpr);

	T visit(ValueExprAST valueExpr);

	T visit(VariableAST variable);


}
