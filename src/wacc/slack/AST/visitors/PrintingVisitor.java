package wacc.slack.AST.visitors;

import wacc.slack.AST.OLD__ExprAST;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.Param;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.statements.ExitStatementAST;

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
	public String toString() {
		return output;
	}

}
