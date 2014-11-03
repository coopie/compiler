package wacc.slack.AST.statements;

import wacc.slack.AST.StatAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class WhileStatementAST extends StatAST {

	private final ExprAST cond;
	private final StatAST body;

	public WhileStatementAST(ExprAST exprAST, StatAST body) {
		addStat(this);
		this.cond = exprAST;
		this.body = body;
	}
	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
