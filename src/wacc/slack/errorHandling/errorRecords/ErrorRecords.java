package wacc.slack.errorHandling.errorRecords;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.errorHandling.expectations.WaccExpectation;

public class ErrorRecords implements Iterable<ErrorObject> {

	private static ErrorRecords INSTANCE;
	
	private final List<ErrorObject> records = new LinkedList<>();
	private final List<WaccExpectation> expectations = new LinkedList<>();
	SymbolTable<IdentInfo> scope;
	
	private ErrorRecords() {
		
	}
	
	public void record(ErrorObject e) {
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
	
	public void setScope(SymbolTable<IdentInfo> scope) {
		this.scope = scope;
	}
	public boolean isErrorFree() {
		boolean ans =  records.size() == 0;
		
		for(WaccExpectation e : expectations) {
			assert scope != null : "scope must be set";
			IdentInfo i = scope.lookup(e.getIdent());
			if(i == null) return false;
			e.setScope(scope);
			ans &= e.check();
		}
		
		return ans;
	}
	
	public int getExitCode() {
		return 100;
	}
	
	@Override
	public Iterator<ErrorObject> iterator() {
		return records.iterator();
	}

	public void addExpectation(WaccExpectation e) {
		expectations.add(e);	
	}
}
