package wacc.slack.AST.literals;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public enum UnaryOpAST implements WaccAST {
	NOT, MINUS, LEN, ORD, CHR ;

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
