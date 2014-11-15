package wacc.slack;

public class FilePosition {

	private final int lineNo;
	private final int columnNo;

	public FilePosition(int lineNo, int columnNo) {
		this.lineNo = lineNo;
		this.columnNo = columnNo;
	}

	public String getFilePosInfo() {
		return lineNo + ":" + columnNo;
	}

	@Override
	public String toString() {
		return getFilePosInfo();
	}

}
