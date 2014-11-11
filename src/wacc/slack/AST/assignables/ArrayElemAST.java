package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class ArrayElemAST implements Assignable, Liter {

	private final String ident;
	// each ExprAST in exprs exits within a separate [] 
	private final List<ExprAST> exprs;
	private final FilePosition filePos;
	private final SymbolTable<IdentInfo> scope;
	
	public ArrayElemAST(String ident, List<ExprAST> exprs, SymbolTable<IdentInfo> scope,FilePosition filePos) {
		this.ident = ident;
		this.exprs = exprs;
		this.scope = scope;
		this.filePos = filePos;

		checkType();
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String getName() {
		return ident;
	}

	@Override
	public Type getType() {
		return scope.lookup(ident).getType();
	}
	

	private void checkType() {
		for(ExprAST expr : exprs) {
			if (!expr.getType().equals(BaseType.T_int)) {
				ErrorRecords.getInstance().record(
						new TypeMismatchError(filePos, expr.getType(), BaseType.T_int));
			}
		}
	}
	
	@Override
	public String getValue() {
		return toString();
	}
	
	public String getIdent() {
		return ident;
	}
	
	public List<ExprAST> getExprs() {
		return exprs;
	}
	
	@Override
	public String toString() {
		return getIdent() + "[" + getExprs() + "]";
	}
	
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(exprs);
	}
}
