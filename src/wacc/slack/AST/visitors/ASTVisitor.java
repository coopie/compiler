package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;

public interface ASTVisitor {
	void visit(StatAST stat);
	void visit(FuncAST func);
	void visit(ExprAST expr);
	void visit(ProgramAST program);
	void visit(AssignRHS assignRHS);
}
