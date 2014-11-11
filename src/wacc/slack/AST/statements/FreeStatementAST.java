package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.IllegalOperationError;

public class FreeStatementAST extends ExprStatementAST {
	
	public FreeStatementAST(ExprAST exprAST, FilePosition filePos) {
		super(exprAST, filePos);
		if (exprAST.getType() != BaseType.T_pair) {
			ErrorRecords.getInstance().record(
					new IllegalOperationError(filePos));
		}
	}

	@Override
	public String getName() {
		return "free";
	}
}
