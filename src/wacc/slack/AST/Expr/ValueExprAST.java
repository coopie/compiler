package wacc.slack.AST.Expr;

import wacc.slack.FilePosition;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ValueExprAST implements ExprAST {

	private final Liter liter;
	private final FilePosition filePos;
	
	public ValueExprAST(Liter l, FilePosition filePos) {
		this.liter = l ;
		this.filePos = filePos;
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	public String getValue() {
		return liter.getValue();
	}
	
	@Override
	public Type getType() {
		return liter.getType();
	}
	
	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);	
	}
	
	public void checkTypes() {
		// Values never have type errors
	}
}
