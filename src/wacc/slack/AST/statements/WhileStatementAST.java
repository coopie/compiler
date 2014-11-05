package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class WhileStatementAST extends StatAST implements WaccAST {

	private final ExprAST cond;
	private final StatAST body;
	public WhileStatementAST(ExprAST exprAST, StatAST body, FilePosition filePos) {
		super(filePos);
		this.cond = exprAST;
		this.body = body;
	}

	@Override 
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
	public ExprAST getCond() {
		return cond;
	}
	
	public StatAST getBody() {
		return body;
	}
	
	@Override
	public String toString() {
	return "while";
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(cond,body));
	}
}
