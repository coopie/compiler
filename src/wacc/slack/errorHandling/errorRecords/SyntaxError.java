package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public class SyntaxError extends ErrorRecord {

	private final String message;

	public SyntaxError(String message, FilePosition fp) {
		super(fp);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public ErrorType getType() {
		return ErrorType.SyntaxError;
	}
}
