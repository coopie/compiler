package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
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
import wacc.slack.AST.statements.ExprStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;

public class PrintingVisitor implements ASTVisitor<String> {

	private int indent = 0;

	private void indent() {
		indent++;
	}

	private void endIndent() {
		indent--;
		if (indent < 0) {
			throw new IllegalStateException("Indentation went wrong");
		}
	}

	private String newLine() {
		String newLine = "\n";
		for (int i = 0; i < indent; i++) {
			newLine += "\t";
		}
		return newLine;
	}

	@Override
	public String visit(ProgramAST program) {
		String r = "";

		r += "start:";
		indent();
		for (FuncAST f : program.getFunctions()) {
			r += f.accept(this);
		}
		r += program.getStatements().accept(this);

		endIndent();
		r += newLine();
		r += "end";
		return r;
	}

	@Override
	public String visit(FuncAST func) {
		String s = func.getType() + " " + func.getIdent() + "()";
		indent();
		s += func.getStat().accept(this);
		endIndent();
		return s;
	}

	@Override
	public String visit(AssignStatAST assignStat) {
		return newLine() + assignStat.getLhs().accept(this) + " = "
				+ assignStat.getRhs().accept(this);
	}

	@Override
	public String visit(BeginEndAST beginEnd) {
		String r = "";

		indent();
		r += beginEnd.getBody().accept(this);
		endIndent();

		return r;
	}

	@Override
	public String visit(IfStatementAST ifStat) {
		String r = newLine();

		r += "if " + ifStat.getCond().accept(this);
		indent();
		r += ifStat.getTrueStats().accept(this);
		endIndent();
		r += newLine() + "else";
		indent();
		r += ifStat.getFalseStats().accept(this);
		endIndent();

		return r;
	}

	@Override
	public String visit(SkipStatementAST skipStat) {
		return newLine() + skipStat.toString();
	}

	@Override
	public String visit(WhileStatementAST whileStat) {
		String r = newLine();

		r += "while (" + whileStat.getCond().accept(this) + ")";
		indent();
		r += whileStat.getBody().accept(this);
		endIndent();

		return r;
	}

	@Override
	public String visit(ArrayElemAST arrayElem) {
		String s = arrayElem.getIdent();
		for (ExprAST expr : arrayElem.getExprs()) {
			s += "[" + expr.accept(this) + "]";
		}
		return s;
	}

	@Override
	public String visit(FstAST fst) {
		return "fst " + fst.getName();
	}

	@Override
	public String visit(SndAST snd) {
		return "snd " + snd.getName();
	}

	@Override
	public String visit(ArrayLiterAST arrayLiter) {
		String r = "[";
		for (WaccAST expr : arrayLiter.getChildren()) {
			r += expr.accept(this) + " ";
		}
		r += "]";
		return r;
	}

	@Override
	public String visit(CallAST call) {
		String r = call.getIdent() + "(";
		for (ExprAST expr : call.getArgList().getExprList()) {
			r += expr.accept(this) + " ";
		}
		r += ")";
		return r;
	}

	@Override
	public String visit(NewPairAST newPair) {
		return "newpair(" + newPair.getExprL() + ", " + newPair.getExprR()
				+ ")";
	}

	@Override
	public String visit(BinaryExprAST binExpr) {
		String r = "(" + binExpr.getExprL().accept(this) + " " + binExpr + " "
				+ binExpr.getExprR().accept(this) + ")";
		return r;
	}

	@Override
	public String visit(UnaryExprAST unExpr) {
		String r = unExpr + "(" + unExpr.getExpr() + ")";
		return r;
	}

	@Override
	public String visit(ValueExprAST valueExpr) {
		return valueExpr.getValue();
	}

	@Override
	public String visit(VariableAST variable) {
		return variable.getName();
	}

	@Override
	public String visit(StatListAST statAST) {
		String r = "";
		for (WaccAST a : statAST.getChildren()) {
			r += a.accept(this);
		}
		return r;
	}

	@Override
	public String visit(ReadStatementAST readStatementAST) {
		return "read " + readStatementAST.getAssignable().accept(this);
	}

	@Override
	public String toString() {
		return "This should never be called now, the visitor can return things now";
	}

	private String visitExprStat(ExprStatementAST exprStat) {
		return newLine() + exprStat.getName() + " "
				+ exprStat.getExpr().accept(this);
	}

	@Override
	public String visit(ReturnStatementAST exprStat) {
		return visitExprStat(exprStat);
	}

	@Override
	public String visit(PrintlnStatementAST printlnStat) {
		return visitExprStat(printlnStat);
	}

	@Override
	public String visit(PrintStatementAST printStat) {
		return visitExprStat(printStat);
	}

	@Override
	public String visit(ExitStatementAST exitStat) {
		return visitExprStat(exitStat);
	}

	@Override
	public String visit(FreeStatementAST freeStat) {
		return visitExprStat(freeStat);
	}
}
