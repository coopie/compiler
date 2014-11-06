package wacc.slack.errorHandling.expectations;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;

public class FunctionReturnTypeExpectation implements WaccExpectation {

	private final String ident;
	private final Type returnType;
	private SymbolTable<IdentInfo> scope;

	public FunctionReturnTypeExpectation(String ident, Type returnType) {
		this.ident = ident;
		this.returnType = returnType;
	}
	
	@Override
	public boolean check() {
		return scope.lookup(ident).getType().equals(returnType);
	}
	
	@Override
	public void setScope(SymbolTable<IdentInfo> scope) {
		this.scope = scope;		
	}

	@Override
	public String getIdent() {
		return ident;
	}

}
