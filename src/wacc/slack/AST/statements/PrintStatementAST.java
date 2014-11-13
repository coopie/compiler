package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class PrintStatementAST extends ExprStatementAST {
	
	public PrintStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
	}

	@Override
	public String getName() {
		return "print";
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
