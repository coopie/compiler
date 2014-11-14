package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.SyntaxError;

public class IntLiter implements Liter {

	private final int i;
	private final FilePosition filePos;

	public IntLiter(long i, FilePosition filePos) {
		if (i < -2147483648 || i > 2147483647) {
			ErrorRecords.getInstance().record(
					new SyntaxError("Integer too big to assign.", filePos));
		}
		this.i = (int) i;
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
