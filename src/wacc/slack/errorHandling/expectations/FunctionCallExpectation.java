package wacc.slack.errorHandling.expectations;

import java.util.Iterator;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecord;

public class FunctionCallExpectation extends ErrorRecord implements
		WaccExpectation {

	private final String ident;
	private final ArgList args;
	private SymbolTable<IdentInfo> scope;

	public FunctionCallExpectation(String ident, ArgList args, FilePosition fp) {
		super(fp);
		this.ident = ident;
		this.args = args;
	}

	public String getIdent() {
		return ident;
	}

	public void setScope(SymbolTable<IdentInfo> scope) {
		this.scope = scope;
	}

	@Override
	public boolean check() {
		if (scope == null) {
			throw new IllegalArgumentException("need scope before checking");
		}

		boolean b = true;
		Iterator<Type> ps = scope.lookup(ident).getParamTypes().iterator();
		Iterator<ExprAST> as = args.iterator();

		while (ps.hasNext() && as.hasNext()) {
			b &= ps.next().equals(as.next().getType());
		}

		b &= !ps.hasNext() && !as.hasNext();
		return b;
	}

	@Override
	public String getMessage() {
		return "params  for " + ident + "do not match " + args;
	}

	@Override
	public ErrorType getType() {
		return ErrorType.ExpectationError;
	}

}
