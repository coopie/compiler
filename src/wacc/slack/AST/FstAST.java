package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class FstAST implements Assignable {
	
	private final ExprAST expr;
	
	public FstAST(ExprAST expr) {
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
		return "fst";
	}
	
	public ExprAST getExpr() {
		return expr;
	}
	

}
