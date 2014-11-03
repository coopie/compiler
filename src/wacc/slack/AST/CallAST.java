package wacc.slack.AST;

public class CallAST implements ParseTreeReturnable {
	
	private final String ident;
	private final ArgList argList;
	
	public CallAST(String ident, ArgList argList) {
		this.ident = ident;
		this.argList = argList;
	}
	
	public String getIdent() {
		return ident;
	}
	
	public ArgList getArgList() {
		return argList;
	}

}
