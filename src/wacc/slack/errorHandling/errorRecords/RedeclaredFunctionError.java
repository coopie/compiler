package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;
import wacc.slack.AST.assignables.FuncAST;

public class RedeclaredFunctionError extends ErrorRecord {

	private final String ident;

	public RedeclaredFunctionError(FilePosition fp, String ident) {
		super(fp);
		this.ident = ident;
	}

	@Override
	public String getMessage() {
		return "Function redeclared: " + FuncAST.decodeFuncName(ident);
	}

	@Override
	public ErrorType getType() {
		return ErrorType.RedeclaredFunctionError;
	}

}
