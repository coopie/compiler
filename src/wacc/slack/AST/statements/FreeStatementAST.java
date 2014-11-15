package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class FreeStatementAST extends ExprStatementAST {

	public FreeStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
		if (!exprAST.getType().equals(new PairType())) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, exprAST.getType(),
							new PairType()));
		}
	}

	@Override
	public String getName() {
		return "free";
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
