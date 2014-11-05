package wacc.slack.AST.assignables;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class VariableAST implements Assignable,ExprAST {

	private final String id;
	private final SymbolTable<IdentInfo> scope;
	private FilePosition filePos;

	public VariableAST(String id, SymbolTable<IdentInfo> scope, FilePosition pos) {
		this.id = id;
		this.scope = scope;
		this.filePos = pos;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public String getName() {
		return id;
	}

	@Override
	public Type getType() {
		return scope.lookup(id).getType();
	}

}
