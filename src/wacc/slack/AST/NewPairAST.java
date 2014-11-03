package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class NewPairAST implements AssignRHS {
	
	private final ExprAST exprL, exprR;
	private final int linePos, charPos;

	public NewPairAST(ExprAST expr1, ExprAST expr2, int linePos, int charPos) {
		this.exprL = expr1;
		this.exprR = expr2;
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
