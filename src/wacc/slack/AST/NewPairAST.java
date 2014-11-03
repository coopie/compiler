package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class NewPairAST implements AssignRHS {
	
	private final ExprAST exprL;
	private final ExprAST exprR;

	public NewPairAST(ExprAST expr1, ExprAST expr2) {
		this.exprL = expr1;
		this.exprR = expr2;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		exprL.accept(visitor);
		exprR.accept(visitor);
		visitor.visit(this);
	}
	
	public ExprAST getExprL() {
		return exprL;
	}

	public ExprAST getExprR() {
		return exprR;
	}
}
