package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public interface ErrorRecord {
	public String getMessage();
	public FilePosition getFilePosition();

}
