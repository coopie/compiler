package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class FstAST implements Assignable {

	private final Assignable expr;
	private final FilePosition filePos;
	private final SymbolTable<IdentInfo> scope;
	private boolean validExpression;

	public FstAST(Assignable expr, FilePosition filePos,
			SymbolTable<IdentInfo> scope) {
		this.expr = expr;
		this.filePos = filePos;
		this.scope = scope;

		validExpression = checkType();
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
		return expr.getName();
	}

	// TODO: this needs changing
	private boolean checkType() {
		if (!(expr.getType() instanceof PairType)) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, expr.getType(),
							new PairType()));
			return false;
		}
		return true;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(expr));
	}

	@Override
	public Type getType() {
		// TODO: sort this out
		if (validExpression) {
			return ((PairType) scope.lookup(expr.getName()).getType()).getFst();
		} else {
			return BaseType.T_int;
		}
	}

	@Override
	public SymbolTable<IdentInfo> getScope() {
		return scope;
	}
}
