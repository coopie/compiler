package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public class RedeclaredFunctionError extends ErrorRecord {

	private final String ident;
	
	public RedeclaredFunctionError(FilePosition fp, String ident) {
		super(fp);
		this.ident = ident;
	}

	@Override
	public String getMessage() {
		return "Function redeclared: " + ident;
	}
	
	@Override
	public ErrorType getType() {
		return ErrorType.RedeclaredFunctionError;
	}

}
