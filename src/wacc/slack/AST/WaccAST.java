package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public interface WaccAST {
	int getPosition();
	void accept(ASTVisitor visitor);
	

}
