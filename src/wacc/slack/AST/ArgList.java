package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.Expr.ExprAST;

public class ArgList implements ParseTreeReturnable {
	
	private final List<ExprAST> exprList;
	private final int linePos, charPos;
	
	public ArgList(List<ExprAST> exprList2, int linePos, int charPos) {
		this.exprList = exprList2;
		this.linePos = linePos;
		this.charPos = charPos;
	}
	
	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}
}
