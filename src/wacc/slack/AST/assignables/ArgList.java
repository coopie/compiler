package wacc.slack.AST.assignables;

import java.util.Iterator;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.Expr.ExprAST;

public class ArgList implements ParseTreeReturnable,Iterable<ExprAST> {
	
	private final List<ExprAST> exprList;
	private final FilePosition filePos;
	
	public ArgList(List<ExprAST> exprList2, FilePosition filePos) {
		this.exprList = exprList2;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

	@Override
	public Iterator<ExprAST> iterator() {
		return exprList.iterator();
	}
}
