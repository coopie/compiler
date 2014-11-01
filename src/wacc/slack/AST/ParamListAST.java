package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class ParamListAST implements WaccAST {

	private List<ParamAST> paramList;
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
	}

	public List<ParamAST> getParamList() {
		return paramList;
	}
}
