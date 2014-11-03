package wacc.slack.AST;

import java.util.List;

public class ParamList implements ParseTreeReturnable {

	private final List<Param> paramList;
	
	public ParamList(List<Param> paramList) {
		this.paramList = paramList;
	}

	public List<Param> getParamList() {
		return paramList;
	}
}
