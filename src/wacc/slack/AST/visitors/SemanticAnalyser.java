package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;

public class SemanticAnalyser implements ASTVisitor {
	
	private SymbolTable<IdentInfo> st;

	public SemanticAnalyser(SymbolTable<IdentInfo> st) {
		this.st = st;
	}

	@Override
	public void visit(StatAST stat) {
		// TODO Auto-generated method stub
		
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
	public void visit(ProgramAST program) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssignRHS assignRHS) {
		// TODO Auto-generated method stub
		
	}

}
