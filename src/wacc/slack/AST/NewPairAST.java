package wacc.slack.AST;

import wacc.slack.AST.Expr.ExprAST;

public class NewPairAST implements ParseTreeReturnable {
	
	private final ExprAST expr1;
	private final ExprAST expr2;

	public NewPairAST(ExprAST expr1, ExprAST expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	public ExprAST getExpr(int i) {
		if (i == 0) {
			return expr1;
		} else if (i == 1) {
			return expr2;
		} else {
			assert false: "new pair only stores 2 expr - access with 0 or 1";
		}
		return null;
	}

}
