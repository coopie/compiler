package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class ExitStatementAST extends ExprStatementAST {

	public ExitStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
		if (exprAST.getType() != BaseType.T_int) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, exprAST.getType(),
							BaseType.T_int));
		}
	}

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
