package wacc.slack.AST.visitors;

import wacc.slack.AST.FuncAST;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.StatAST;

public interface ASTVisitor {
	void visit(ProgramAST program);
	void visit(StatAST stat);
	void visit(FuncAST func);
	

}
