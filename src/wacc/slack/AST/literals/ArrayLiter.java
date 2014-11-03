package wacc.slack.AST.literals;

import java.util.List;

import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.Expr.ExprAST;

public class ArrayLiter implements ParseTreeReturnable {
	
	private final List<ExprAST> exprList;
	
	public ArrayLiter(List<ExprAST> exprList2) {
		this.exprList = exprList2;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

}
