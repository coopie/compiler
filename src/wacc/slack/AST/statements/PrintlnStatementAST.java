package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class PrintlnStatementAST extends ExprStatementAST {
	
	public PrintlnStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	public String getName() {
		return "println";
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
