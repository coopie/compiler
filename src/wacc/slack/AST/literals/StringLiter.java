package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;

public class StringLiter implements Liter {

	private final String text;
	private final FilePosition filePos;

	public StringLiter(String text, FilePosition filePost) {
		this.text = text.substring(1,text.length() -1);
		this.filePos = filePost;
	}

	@Override
	public Type getType() {
		return new WaccArrayType(BaseType.T_char);
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
