package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
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
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatAST;
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
	
	private void newLine() {
		String newLine = "\n";
		for(int i = 0; i < indent; i++) {
			newLine += "\t";
		}
		output += newLine;
	}

	@Override
	public String visit(ProgramAST program) {
		output += "start:";
		indent();
		for(FuncAST f : program.getFunctions()) {
			newLine();
			f.accept(this);
		}
		endIndent();
		program.getStatements().accept(this);

		output +="\nend";
		return null;
	}
	
	@Override
	public String visit(FuncAST func) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(AssignStatAST assignStat) {
		output += assignStat;
		newLine();
		return null;
	}

	@Override
	public String visit(BeginEndAST beginEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(IfStatementAST ifStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(SkipStatementAST skipStat) {
		output += skipStat.toString();
		return null;
	}

	@Override
	public String visit(WhileStatementAST whileStat) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
	public String visit(StatAST statAST) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return output;
	}
}
