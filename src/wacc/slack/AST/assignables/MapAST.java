package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;
import wacc.slack.errorHandling.expectations.FunctionCallExpectation;

public class MapAST implements AssignRHS {

	private final String array;
	private final String function;
	private final SymbolTable<IdentInfo> scope;
	private final FilePosition filePos;
	private WaccArrayType arrayType;
	
	public MapAST(String array, String function, SymbolTable<IdentInfo> scope, final FilePosition filePos) {
		this.array = array;
		this.function = function;
		this.scope = scope;
		this.filePos = filePos;
		
		IdentInfo ident = scope.lookup(array);
		
		if(!ident.getType().equals(new WaccArrayType())) {
			ErrorRecords.getInstance().record(new TypeMismatchError(filePos, ident.getType(), new WaccArrayType()));
		}
		
		arrayType = (WaccArrayType)ident.getType();
		
		ErrorRecords.getInstance().addExpectation(
				new FunctionCallExpectation(function,new ArgList(new LinkedList<ExprAST>(Arrays.asList(new ExprAST(){

					@Override
					public <T> T accept(ASTVisitor<T> visitor) {
						return null;
					}

					@Override
					public List<WaccAST> getChildren() {
						return new LinkedList<>();
					}

					@Override
					public FilePosition getFilePosition() {
						return filePos;
					}

					@Override
					public Type getType() {
						return arrayType.getType();
					}})), filePos), filePos));		
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>();
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	public WaccArrayType getSourceArrayType() {
		return arrayType;
	}
	
	@Override
	public Type getType() {
		return getDestType();
	}
	
	public WaccArrayType getDestType() {
		return new WaccArrayType(getScope().lookup(getFunction()).getType());
	}

	public String getArray() {
		return array;
	}

	public String getFunction() {
		return function;
	}

	public SymbolTable<IdentInfo> getScope() {
		return scope;
	}

}
