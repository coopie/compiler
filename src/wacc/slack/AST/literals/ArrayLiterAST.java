package wacc.slack.AST.literals;

import java.util.List;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayLiterAST implements WaccAST {
	
	private final List<ExprAST> exprList;
	
	public ArrayLiterAST(List<ExprAST> exprList) {
		this.exprList = exprList;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

}
