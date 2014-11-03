package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class SndAST implements Assignable {
	
	private final ExprAST expr;
	
	public SndAST(ExprAST expr) {
		this.expr = expr;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
	}

	@Override
	public String getName() {
		return "snd";
	}
	
	public ExprAST getExpr() {
		return expr;
	}

}
