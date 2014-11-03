package wacc.slack.AST.visitors;

import wacc.slack.AST.FuncAST;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.CharLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.literals.StringLiter;

public interface ASTVisitor {
	void visit(StatAST stat);
	void visit(FuncAST func);
	void visit(ExprAST expr);
	void visit(ProgramAST program);
}
