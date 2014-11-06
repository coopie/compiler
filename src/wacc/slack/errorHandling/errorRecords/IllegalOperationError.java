package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

//currently only used for return statements outside of funciton calls
public class IllegalOperationError extends ErrorRecord {

	public IllegalOperationError(FilePosition fp) {
		super(fp);
	}

	@Override
	public String getMessage() {
		return "Illegal use of return outside of a fucntion";
	}

	

}
