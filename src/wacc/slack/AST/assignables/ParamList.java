package wacc.slack.AST.assignables;

import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.ParseTreeReturnable;

public class ParamList implements ParseTreeReturnable {

	private final List<Param> paramList;
	private final FilePosition filePos;

	public ParamList(List<Param> paramList, FilePosition filePos) {
		this.paramList = paramList;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	public List<Param> getParamList() {
		return paramList;
	}
}
