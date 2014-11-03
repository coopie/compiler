package wacc.slack.AST.visitors;

import wacc.slack.AST.AssignRHS;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.Expr.ExprAST;

public class PrintingVisitor implements ASTVisitor {

	private String output = "";

	@Override
	public void visit(ProgramAST program) {
		output += "start:";
		for(FuncAST f : program.getFunctions()) {
			f.accept(this);
		}
		program.getStatements().accept(this);

		output +="\nend";
	}

	@Override
	public void visit(StatAST stat) {
		output += "\n\t" + stat;

	}

	@Override
	public void visit(FuncAST func) {
		output += "\n" + func.toString();
	}
	
	@Override
	public void visit(ExprAST expr) {
		output += " " + expr.toString();
	}
	
	@Override
	public String toString() {
		return output;
	}

	@Override
	public void visit(AssignRHS assignRHS) {
		// TODO Auto-generated method stub
		
	}

}
