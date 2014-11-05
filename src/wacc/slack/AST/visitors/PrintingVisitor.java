package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
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
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;

public class PrintingVisitor implements ASTVisitor<String> {

	private String output = "";
	
	private int indent = 0;
	
	private void indent() {
		indent++;
	}
	private void endIndent() {
		indent--;
		if(indent < 0) {
			throw new IllegalStateException("Indentation went wrong");
		}
	}
	
	private String newLine() {
		String newLine = "\n";
		for(int i = 0; i < indent; i++) {
			newLine += "\t";
		}
		output += newLine;
		return newLine;
	}
	public void clear() {
		output = "";
	}
	@Override
	public String visit(ProgramAST program) {
		String r = "";
		
		r += "start:";
		indent();
		for(FuncAST f : program.getFunctions()) {
			r += newLine();
			r += f.accept(this);
		}
		r += newLine();
		r += program.getStatements().accept(this);

		endIndent();
		r += newLine();
		r += "end";
		output = r;
		return r;
	}
	
	@Override
	public String visit(FuncAST func) {
		output += func.getType() + " " + func.getIdent() + "()";
		indent();
		output += newLine();
		output += func.getStat().accept(this);
		endIndent();
		return output;
	}

	@Override
	public String visit(AssignStatAST assignStat) {
		return null;
	}

	@Override
	public String visit(BeginEndAST beginEnd) {
		String r = "";
		
		indent();
		r += beginEnd.getBody().accept(this);
		endIndent();
		
		output = r;
		return r;
	}

	@Override
	public String visit(IfStatementAST ifStat) {
		String r = "";
		
		r += "if " + ifStat.getCond().accept(this);
		r += newLine();
		indent();
		r += ifStat.getTrueStats().accept(this);
		endIndent();
		r += "else" + newLine();
		indent();
		r += ifStat.getFalseStats().accept(this);
		endIndent();
		
		output = r;
		return r;
	}

	@Override
	public String visit(SkipStatementAST skipStat) {
		output = skipStat.toString();
		return output;
	}

	@Override
	public String visit(WhileStatementAST whileStat) {
		String r = "";
		
		r += "while (" + whileStat.getCond().accept(this) + ")";
		indent();
		r += whileStat.getBody().accept(this);
		endIndent();
		
		output = r;
		return r;
	}

	@Override
	public String visit(ArrayElemAST arrayElem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(FstAST fst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(SndAST snd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(ArrayLiterAST arrayLiter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(CallAST call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(NewPairAST newPair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(BinaryExprAST binExpr) {
		String r = "("
	            + binExpr.getExprL().accept(this) + " "
				+ binExpr + " "
				+ binExpr.getExprR().accept(this) +
				")";
		
		output = r;
		return r;
	}

	@Override
	public String visit(UnaryExprAST unExpr) {
		String r = unExpr + "(" + unExpr.getExpr() + ")"; 
		output += r;		
		return r;
	}

	@Override
	public String visit(ValueExprAST valueExpr) {
		output = valueExpr.getValue();
		return valueExpr.getValue();
	}

	@Override
	public String visit(VariableAST variable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String visit(StatListAST statAST) {
		output = "";
		for(WaccAST a : statAST.getChildren()) {
			output += newLine();
			output += a.accept(this);
		}
		return output;
	}
	
	@Override
	public String toString() {
		return output;
	}
	@Override
	public String visit(ExprStatementAST exprStat) {
		output = exprStat.getName() + " " + exprStat.getExpr().accept(this);
		return output;
	}
}
