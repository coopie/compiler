package wacc.slack.AST.assignables;

import java.util.Iterator;
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
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class ArrayElemAST implements Assignable, Liter {

	private final String ident;
	// each ExprAST in exprs exits within a separate [] 
	private final List<ExprAST> exprs;
	private final FilePosition filePos;
	private final Type type;
	
	public ArrayElemAST(String ident, List<ExprAST> exprs, SymbolTable<IdentInfo> scope,FilePosition filePos) {
		this.ident = ident;
		this.exprs = exprs;
		this.filePos = filePos;
		
		IdentInfo info = scope.lookup(ident);
		Type t = info.getType();
		Iterator<ExprAST> i = exprs.iterator();
		
		while(i.hasNext()) {
			if(!(t instanceof WaccArrayType)) {
				throw new RuntimeException("array type of identifier in array elem is not valid");
			}
			t = ((WaccArrayType) t).getType();
			i.next();
		}

		type = t;
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
		return type;
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
		String s = getIdent();
		for(ExprAST expr : getExprs()) {
			s += "[" + expr + "]";
		}
		return s;
	}
	
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(exprs);
	}
}
