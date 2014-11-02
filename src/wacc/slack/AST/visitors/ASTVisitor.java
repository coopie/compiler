package wacc.slack.AST.visitors;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.FuncAST;
import wacc.slack.AST.ParamAST;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;
import wacc.slack.AST.literals.BoolLiterAST;
import wacc.slack.AST.literals.IntLiterAST;
import wacc.slack.AST.literals.PairLiterAST;

public interface ASTVisitor {
	void visit(ProgramAST program);
	void visit(StatAST stat);
	void visit(FuncAST func);
	void visit(ExprAST expr);
	void visit(BoolLiterAST boolLiter);
	void visit(IntLiterAST intLiter);
	void visit(PairLiterAST pairLiter);
	void visit(ParamAST paramAST);
}
