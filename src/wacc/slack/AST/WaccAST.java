package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public interface WaccAST extends ParseTreeReturnable {

	public void accept(ASTVisitor visitor);
	//returns a list of child nodes, do note that the class must also provide getters for the children
	//The children are returned in such a way to conform to inorder traversal of the tree
	//public List<WaccAST> getChildren();
}
