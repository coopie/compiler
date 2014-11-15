package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public class IntegerOverflow extends ErrorRecord {

	public IntegerOverflow(FilePosition fp) {
		super(fp);
	}

	@Override
	public String getMessage() {
		return null;
	}

}
