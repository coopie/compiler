package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;

public class PrintingVisitor implements ASTVisitor<Void> {

	private String output = "";

	@Override
	public Void visit(ProgramAST program) {
		output += "start:";
		for(FuncAST f : program.getFunctions()) {
			f.accept(this);
		}
		program.getStatements().accept(this);

		output +="\nend";
		return null;
	}

	@Override
	public Void visit(StatAST stat) {
		output += "\n\t" + stat;
		return null;
	}

	@Override
	public Void visit(FuncAST func) {
		output += "\n" + func.toString();
		return  null;
	}
	
	@Override
	public Void visit(ExprAST expr) {
		output += " " + expr.toString();
		return null;
	}
	
	@Override
	public String toString() {
		return output;
	}

	@Override
	public Void visit(AssignRHS assignRHS) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
