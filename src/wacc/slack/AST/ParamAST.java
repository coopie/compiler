package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public class ParamAST implements WaccAST{
	
	private String ident;

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
	}
}