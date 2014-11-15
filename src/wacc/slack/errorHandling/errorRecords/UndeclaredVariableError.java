package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

// To be used with RedeclaredVariable in symbol Table
public class UndeclaredVariableError extends ErrorRecord {

	private final String ident;

	public UndeclaredVariableError(FilePosition fp, String ident) {
		super(fp);
		this.ident = ident;
	}

	@Override
	public String getMessage() {
		return "Undeclared variable: " + ident;
	}

	@Override
	public ErrorType getType() {
		return ErrorType.UndeclaredVariableError;
	}

}
