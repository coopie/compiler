package wacc.slack.AST.literals;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayLiter implements Liter, AssignRHS {
	
	private final List<ExprAST> exprList;
	private final WaccArrayType type;
	private final FilePosition filePos;
	
	public ArrayLiter(List<ExprAST> exprList, FilePosition filePos, WaccArrayType type) {
		this.exprList = exprList;
		this.filePos = filePos;
		this.type = type;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

	@Override
	public Type getType() {
		return (Type)type;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public void accept(ASTVisitor<?> visitor) {
		visitor.visit(this);
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(exprList);
	}

}
