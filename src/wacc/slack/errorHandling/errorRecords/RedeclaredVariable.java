package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

// to be used with UndeclaredVariable in symbol Table
public class RedeclaredVariable extends ErrorRecord {
	
	private final String ident;
	
	public RedeclaredVariable(FilePosition fp, String ident) {
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
