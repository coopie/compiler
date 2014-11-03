package wacc.slack.AST.visitors;

import wacc.slack.AST.OLD__ExprAST;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.Param;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.CharLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.literals.StringLiter;

public class PrintingVisitor implements ASTVisitor {

	private String output = "";

	@Override
	public void visit(ProgramAST program) {
		output += "start:\n";
		for(FuncAST f : program.getFunctions()) {
			output += "\t";
			f.accept(this);
		}
		output += "\t";
		program.getStatements().accept(this);
		output +="end";
	}

	@Override
	public void visit(StatAST stat) {
		output += stat + "\n";

	}

	@Override
	public void visit(FuncAST func) {
		output += func.toString();
	}
	
	@Override
	public void visit(ExprAST expr) {
		output += " " + expr.toString();
	}
	
	@Override
	public String toString() {
		return output;
	}

}
