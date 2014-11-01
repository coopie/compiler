package wacc.slack;

import java.util.LinkedList;
import java.util.List;

public class ErrorRecords {

	private static ErrorRecords INSTANCE;
	
	private final List<ErrorRecord> records = new LinkedList<>();
	
	private ErrorRecords() {
		
	}
	
	public void record(ErrorRecord e) {
		records.add(e);
	}
	
	public static ErrorRecords getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ErrorRecords();
		}
		
		return INSTANCE;
	}
}
