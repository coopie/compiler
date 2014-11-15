package wacc.slack.errorHandling.expectations;

import wacc.slack.FilePosition;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecord;

public class FunctionReturnTypeExpectation extends ErrorRecord implements
		WaccExpectation {

	private final String ident;
	private final Type returnType;
	private SymbolTable<IdentInfo> scope;

	public FunctionReturnTypeExpectation(String ident, Type returnType,
			FilePosition fp) {
		super(fp);
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

	@Override
	public ErrorType getType() {
		return ErrorType.ExpectationError;
	}

	@Override
	public String getMessage() {
		return "return type of " + ident + " doesn't match "
				+ returnType.toString();
	}

}
