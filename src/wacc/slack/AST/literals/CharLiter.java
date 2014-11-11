package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.SyntaxError;

public class CharLiter implements Liter {

	private final char text;
	private final FilePosition filePos;

	public CharLiter(String text, FilePosition filePos) {
		// TODO: Need to check that this is fine
		this.text = text.charAt(0);
		this.filePos = filePos;

		checkEscapedChars();
	}

	@Override
	public Type getType() {
		return BaseType.T_char;
	}

	@Override
	public String getValue() {
		return text + "";
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	private void checkEscapedChars() {
		// Should be '\"'
		if (text == '"') { 
			ErrorRecords.getInstance().record(
					new SyntaxError("Unescaped character error", filePos));
		}
	}
}
