package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.SyntaxError;

public class CharLiter implements Liter {

	private final String text;
	private final FilePosition filePos;

	public CharLiter(String text, FilePosition filePos) {
		this.filePos = filePos;
		// Remove single quotes
		this.text = text.substring(1, text.length() - 1);
		checkEscapedChars(this.text);
	}

	private void checkEscapedChars(String s) {
		if (s.length() == 1
				&& (s.charAt(0) == '"' || s.charAt(0) == '\'' || s.charAt(0) == '\\')) {
			ErrorRecords.getInstance().record(
					new SyntaxError("Unescaped character error", filePos));
		}
		if (s.charAt(0) == '\\') {
			switch (s.charAt(1)) {
			case ('0'):
			case ('b'):
			case ('t'):
			case ('n'):
			case ('f'):
			case ('r'):
			case ('\''):
			case ('\\'):
				break;
			default:
				ErrorRecords.getInstance().record(
						new SyntaxError("Bad escaped character error", filePos));
			}
		}
	}

	@Override
	public Type getType() {
		return BaseType.T_char;
	}

	@Override
	public String getValue() {
		return text;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
}
