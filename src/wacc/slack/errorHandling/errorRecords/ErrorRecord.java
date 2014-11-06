package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public abstract class ErrorRecord implements ErrorObject {
	
	protected final FilePosition fp;
	
	public ErrorRecord(FilePosition fp) {
		this.fp = fp;
	}
	
	@Override
	public abstract String getMessage();
	
	@Override
	public FilePosition getFilePosition() {
		return fp;
	}

}
