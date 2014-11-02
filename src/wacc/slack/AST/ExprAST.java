package wacc.slack.AST;

import wacc.slack.AST.literals.LiterAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ExprAST implements WaccAST{

	LiterAST literAST;
	ExprAST expr;
	
	public ExprAST(LiterAST literAST) {
		this.literAST = literAST;
	}

	public ExprAST(ExprAST expr, WaccAST unaryOper) {
		// TODO Auto-generated constructor stub
	}
	
	public ExprAST(ExprAST expr, ExprAST expr2,
			WaccAST binaryOper) {
		// TODO Auto-generated constructor stub
	}

	public ExprAST(ExprAST expr) {
		this.expr = expr;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
