package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public interface WaccAST extends ParseTreeReturnable {
	int getPosition();
	void accept(ASTVisitor visitor);
	

}
