package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class ArgList implements ParseTreeReturnable {
	
	private final List<ExprAST> exprList;
	
	public ArgList(List<ExprAST> exprList) {
		this.exprList = exprList;
	}

	
	public List<ExprAST> getExprList() {
		return exprList;
	}

}
