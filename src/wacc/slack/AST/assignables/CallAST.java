package wacc.slack.AST.assignables;

import wacc.slack.AST.visitors.ASTVisitor;

public class CallAST implements AssignRHS {
	
	private final String ident;
	private final ArgList argList;
	private final int linePos, charPos;
	
	public CallAST(String ident, ArgList argList, int linePos, int charPos) {
		this.ident = ident;
		this.argList = argList;
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
