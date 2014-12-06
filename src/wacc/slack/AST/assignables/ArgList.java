package wacc.slack.AST.assignables;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.Expr.ExprAST;

public class ArgList implements ParseTreeReturnable, Iterable<ExprAST> {

	private final Deque<ExprAST> exprList;
	private final FilePosition filePos;

	public ArgList(Deque<ExprAST> exprList2, FilePosition filePos) {
		this.exprList = exprList2;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	public Deque<ExprAST> getExprList() {
		return exprList;
	}

	@Override
	public Iterator<ExprAST> iterator() {
		return exprList.iterator();
	}

	@Override
	public String toString() {
		String s = "";
		for (ExprAST e : exprList) {
			s += e.toString();
		}
		if (s == "")
			return "void";
		return s;
	}
}
