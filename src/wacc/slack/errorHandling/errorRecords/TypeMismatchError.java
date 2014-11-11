package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.Type;

public class TypeMismatchError extends ErrorRecord {

	private final Type[] expected;
	private final Type actual;
	
	public TypeMismatchError(FilePosition fp, Type actual, Type... expected) {
		super(fp);
		this.actual = actual;
		this.expected = expected;
	}

	@Override
	public String getMessage() {
		String message =  "Mismatched type, expected: " + printExpected() +
				 ", got: " + actual;
		
		return message;
	}
	
	private String printExpected() {
		String s = "";
		for(Type t : expected) {
			s += t + " ";
		}
		return s;
	}

	@Override
	public FilePosition getFilePosition() {
		return fp;
	}
	
	@Override
	public ErrorType getType() {
		return ErrorType.TypeMismatchError;
	}

}
