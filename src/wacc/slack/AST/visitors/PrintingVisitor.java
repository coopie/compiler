package wacc.slack.AST.visitors;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.literals.BoolLiterAST;
import wacc.slack.AST.literals.IntLiterAST;

public class PrintingVisitor implements ASTVisitor {

	private String output = "";

	@Override
	public void visit(ProgramAST program) {
		output += "start:\n";
		for(FuncAST f : program.getFunctions()) {
			output += "\t";
			visit(f);
		}
		output += "\t";
		visit(program.getStatements());
		output +="end";
	}

	@Override
	public void visit(StatAST stat) {
		output += stat + "\n";

	}

	@Override
	public void visit(FuncAST func) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void visit(ExprAST expr) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(BoolLiterAST boolLiter) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void visit(IntLiterAST intLiter) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		return output;
	}
}
