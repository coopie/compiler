package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;

public class PairLiter implements Liter {

	private final FilePosition filePos;
	
	public PairLiter(FilePosition filePos) {
		this.filePos = filePos;
	}

	@Override
	public Type getType() {
		return new PairType(null,null, filePos);
	}

	@Override
	public String getValue() {
		return "NULL";
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
}
