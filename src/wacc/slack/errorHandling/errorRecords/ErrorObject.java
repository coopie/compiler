package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public interface ErrorObject {
	
	public String getMessage();
	public FilePosition getFilePosition();

}