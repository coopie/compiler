package wacc.slack.errorHandling.errorRecords;

import java.io.OutputStream;

public class ErrorRecordPrinter {

	private final ErrorRecords ers;
	
	public ErrorRecordPrinter(ErrorRecords ers, OutputStream os) {
		this.ers = ers;
	}
	
	public void print() {
		//TODO
	}

}
