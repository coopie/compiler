package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

// To be used with UndeclaredVariable in symbol Table
public class RedeclaredVariableError extends ErrorRecord {

	private final String ident;

	public RedeclaredVariableError(FilePosition fp, String ident) {
		super(fp);
		this.ident = ident;
	}

	@Override
	public String getMessage() {
		return "Variable redeclared in same scope: " + ident;
	}

	@Override
	public ErrorType getType() {
		return ErrorType.RedeclaredVariableError;
	}

}
