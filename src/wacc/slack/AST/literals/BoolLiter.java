package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class BoolLiter implements Liter {

	private final String value;
	private final FilePosition filePos;
	
	public BoolLiter(String value, FilePosition filePos) {
		this.value = value;
		this.filePos = filePos;

	}

	@Override
	public Type getType() {
		return BaseType.T_bool;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
}
