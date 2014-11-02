package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class ArgListAST implements WaccAST {
	
	private final List<ExprAST> exprList;
	
	public ArgListAST(List<ExprAST> exprList) {
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
