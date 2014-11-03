package wacc.slack.AST;

import java.util.List;

public class ParamList implements ParseTreeReturnable {

	private final List<Param> paramList;
	private final int linePos, charPos;
	
	public ParamList(List<Param> paramList, int linePos, int charPos) {
		this.paramList = paramList;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public int getLine() {
		return linePos;
	}

	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	public List<Param> getParamList() {
		return paramList;
	}
}
