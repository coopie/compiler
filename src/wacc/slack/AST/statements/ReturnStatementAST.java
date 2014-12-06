package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ReturnStatementAST extends ExprStatementAST {

	private final String function;

	public ReturnStatementAST(ExprAST exprAST, String currentFunction, FilePosition filePos) {
		super(exprAST, filePos);
		this.function = currentFunction;
	}

	@Override
	public String getName() {
		return "return";
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public String getFunction() {
		return function;
	}

}
