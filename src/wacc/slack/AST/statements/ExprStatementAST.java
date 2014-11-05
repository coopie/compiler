package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public abstract class ExprStatementAST extends StatAST implements WaccAST {

	private final ExprAST expr;
	public ExprStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(filePos);
		this.expr = exprAST;
	}

	public abstract String getName();
	
	@Override
	public String toString() {
		return getName() + " " + getExpr().toString();
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(getExpr()));
	}

	public ExprAST getExpr() {
		return expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor){
		return visitor.visit(this);
	}

}