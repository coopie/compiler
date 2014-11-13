package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class StringLiter implements Liter {

	private final String text;
	private final FilePosition filePos;
	
	//TODO: Remove double quotes around the text
	
	public StringLiter(String text, FilePosition filePost) {
		this.text = text;
		this.filePos = filePost;
	}

	@Override
	public Type getType() {
		return BaseType.T_string;
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
