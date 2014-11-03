package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public class CallAST implements AssignRHS {
	
	private final String ident;
	private final ArgList argList;
	
	public CallAST(String ident, ArgList argList) {
		this.ident = ident;
		this.argList = argList;
	}
	
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	public String getIdent() {
		return ident;
	}
	
	public ArgList getArgList() {
		return argList;
	}
}
