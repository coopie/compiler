package wacc.slack.errorHandling.errorRecords;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.errorHandling.expectations.WaccExpectation;

public class ErrorRecords implements Iterable<ErrorRecord> {

	private static ErrorRecords INSTANCE;
	
	private final List<ErrorRecord> records = new LinkedList<>();
	private final List<WaccExpectation> expectations = new LinkedList<>();
	SymbolTable<IdentInfo> scope;
	
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
	
	protected static ErrorRecords getInstance(boolean reset) {
		if(INSTANCE == null || reset) {
			INSTANCE = new ErrorRecords();
		}
		
		return INSTANCE;
	}

	public boolean isErrorFree() {
		boolean ans =  records.size() == 0;
		
		for(WaccExpectation e : expectations) {
			IdentInfo i = scope.lookup(e.getIdent());
			if(i == null) return false;
			e.setScope(scope);
			ans &= e.check();
		}
		
		return ans;
	}
	
	@Override
	public Iterator<ErrorRecord> iterator() {
		return records.iterator();
	}

	public void addExpectation(WaccExpectation e) {
		expectations.add(e);	
	}
}
