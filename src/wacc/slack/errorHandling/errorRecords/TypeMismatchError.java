package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;

public class TypeMismatchError extends ErrorRecord {

	private final Type expected;
	private final Type actual;
	
	public TypeMismatchError(Type expected, Type actual, FilePosition fp) {
		super(fp);
		this.actual = actual;
		this.expected = expected;
	}

	@Override
	public String getMessage() {
		String message =  "ERROR: line " + fp.getFilePosInfo() +
				 "\n mismatched type, expected: " + expected +
				 ", got: " + actual;
		
		return message;
	}

	@Override
	public FilePosition getFilePosition() {
		return fp;
	}

}
