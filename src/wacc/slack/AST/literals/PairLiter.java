package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class PairLiter implements Liter {

	private final FilePosition filePos;
	
	public PairLiter(FilePosition filePos) {
		this.filePos = filePos;
	}

	@Override
	public Type getType() {
		return BaseType.T_pair;
	}

	@Override
	public String getValue() {
		return "null";
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
}
