package wacc.slack.errorHandling.errorRecords;

import wacc.slack.FilePosition;

public interface ErrorObject {

	public enum ErrorType {
		ErrorRecord, IllegalOperationError, RedeclaredVariableError, TypeMismatchError, UndeclaredVariableError, SyntaxError, ExpectationError, RedeclaredFunctionError
	}

	public ErrorType getType();

	public String getMessage();

	public FilePosition getFilePosition();

}