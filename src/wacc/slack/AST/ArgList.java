package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.Expr.ExprAST;

public class ArgList implements ParseTreeReturnable {
	
	private final List<ExprAST> exprList;
	
	public ArgList(List<ExprAST> exprList2) {
		this.exprList = exprList2;
	}

	
	public List<ExprAST> getExprList() {
		return exprList;
	}

}
