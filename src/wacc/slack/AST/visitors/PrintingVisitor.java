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
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.WhileStatementAST;

public class PrintingVisitor implements ASTVisitor<Void> {

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
	public Void visit(ProgramAST program) {
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
	public Void visit(FuncAST func) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(AssignStatAST assignStat) {
		output += assignStat;
		newLine();
		return null;
	}

	@Override
	public Void visit(BeginEndAST beginEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(IfStatementAST ifStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SkipStatementAST skipStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(WhileStatementAST whileStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ArrayElemAST arrayElem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(FstAST fst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SndAST snd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ArrayLiterAST arrayLiter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(CallAST call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(NewPairAST newPair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(BinaryExprAST binExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(UnaryExprAST unExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(ValueExprAST valueExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(VariableAST variable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return output;
	}



}
