package wacc.slack.AST.literals;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class IntLiterAST implements WaccAST {

	private final int i;
	
	public IntLiterAST(int i) {
		this.i = i;
	}
	
	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
	}

	public int getInt() {
		return i;
	}

}
