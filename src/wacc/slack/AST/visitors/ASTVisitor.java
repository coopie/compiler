package wacc.slack.AST.visitors;

import wacc.slack.AST.ArgList;
import wacc.slack.AST.ExprAST;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.Param;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.literals.ArrayLiter;
import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.PairLiter;

public interface ASTVisitor {
	void visit(ProgramAST program);
	void visit(StatAST stat);
	void visit(FuncAST func);
	void visit(ExprAST expr);

}
