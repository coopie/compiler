package wacc.slack.AST.literals;

import java.util.List;

import wacc.slack.AST.AssignRHS;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayLiter implements Liter, AssignRHS {
	
	private final List<ExprAST> exprList;
	private final int linePos, charPos;
	
	public ArrayLiter(List<ExprAST> exprList, int linePos, int charPos) {
		this.exprList = exprList;
		this.linePos = linePos;
		this.charPos = charPos;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

	@Override
	public Type getType() {
		return exprList.get(0).getType();
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLine() {
		return linePos;
	}
	
	@Override
	public int getCharColumn() {
		return charPos;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
	}

}
