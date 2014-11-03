package wacc.slack.AST.literals;

import java.util.List;

import wacc.slack.AST.AssignRHS;
import wacc.slack.AST.ParseTreeReturnable;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayLiter implements Liter, AssignRHS {
	
	private final List<ExprAST> exprList;
	
	public ArrayLiter(List<ExprAST> exprList2) {
		this.exprList = exprList2;
	}
	
	public List<ExprAST> getExprList() {
		return exprList;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
