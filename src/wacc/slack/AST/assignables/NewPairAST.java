package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class NewPairAST implements AssignRHS {
	
	private final ExprAST exprL, exprR;
	private final FilePosition filePos;

	public NewPairAST(ExprAST expr1, ExprAST expr2, FilePosition filePos) {
		this.exprL = expr1;
		this.exprR = expr2;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	public ExprAST getExprL() {
		return exprL;
	}

	public ExprAST getExprR() {
		return exprR;
	}
	
	@Override
	public String toString() {
		return exprL + ", " + exprR;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(exprL,exprR));
	}
}
