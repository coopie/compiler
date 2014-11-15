package wacc.slack.errorHandling.errorRecords;

import java.io.PrintStream;

public class ErrorRecordPrinter {

	private final ErrorRecords ers;
	PrintStream ps;

	public ErrorRecordPrinter(ErrorRecords ers, PrintStream ps) {
		this.ers = ers;
		this.ps = ps;
	}

	public void print() {
		for (ErrorObject eo : ers) {
			ps.println("ERROR: at " + eo.getFilePosition().getFilePosInfo()
					+ ":");
			ps.println(eo.getMessage());
		}
	}

}
