package wacc.slack.AST.assignables;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
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
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
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
		if (scope.lookup(id) != null) {
			return scope.lookup(id).getType();
		}
		return BaseType.T_int;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>();
	}
}
