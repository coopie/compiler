package wacc.slack.AST.assignables;

import wacc.slack.FilePosition;
import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;

public class Param implements ParseTreeReturnable {

	private final String ident;
	private final Type type;
	private final FilePosition filePos;
	private final SymbolTable<IdentInfo> scope;

	public Param(String ident, Type type, SymbolTable<IdentInfo> scope, FilePosition filePos) {
		this.ident = ident;
		this.type = type;
		this.scope = scope;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	public String getIdent() {
		return ident;
	}

	public Type getType() {
		return type;
	}

	public SymbolTable<IdentInfo> getScope() {
		return scope;
	}

}
