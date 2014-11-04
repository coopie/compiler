package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public class IntLiter implements Liter {

	private final int i;
	private final FilePosition filePos;
	
	public IntLiter(int i, FilePosition filePos) {
		this.i = i;
		this.filePos = filePos;
	}

	@Override
	public Type getType() {
		return BaseType.T_int;
	}
	
	@Override
	public String toString() {
		return "" + i;
	}

	@Override
	public String getValue() {
		return Integer.toString(i);
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	public int getInt() {
		return i;
	}

}
