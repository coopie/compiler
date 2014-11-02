package wacc.slack.AST.literals;

import java.util.List;

import wacc.slack.AST.ExprAST;
import wacc.slack.AST.ParseTreeReturnable;

public class ArrayLiter implements ParseTreeReturnable {
	
	private final List<ExprAST> exprList;
	
	public ArrayLiter(List<ExprAST> exprList) {
		this.exprList = exprList;
	}

	
	public List<ExprAST> getExprList() {
		return exprList;
	}

}
