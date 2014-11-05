package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;

public abstract class StatAST implements WaccAST{

	protected final FilePosition filePos;

	public StatAST(FilePosition filePos) {
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

}