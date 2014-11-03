package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public interface WaccAST extends ParseTreeReturnable {

	public void accept(ASTVisitor visitor);
}
