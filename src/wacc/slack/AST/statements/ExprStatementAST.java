package wacc.slack.AST.statements;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public abstract class ExprStatementAST extends StatAST {

	protected final ExprAST expr;

	public ExprStatementAST(ExprAST exprAST, int linePos, int charPos) {
		super(linePos, charPos);
		addStat(this);
		this.expr = exprAST;
	}

	protected abstract String getName();
	
	@Override
	public String toString() {
		return getName() + " " + expr.toString();
	}
	
	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}