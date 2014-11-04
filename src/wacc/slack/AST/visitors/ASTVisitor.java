package wacc.slack.AST.visitors;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;

public interface ASTVisitor<T> {
	T visit(StatAST stat);
	T visit(FuncAST func);
	T visit(ExprAST expr);
	T visit(ProgramAST program);
	T visit(AssignRHS assignRHS);
}
