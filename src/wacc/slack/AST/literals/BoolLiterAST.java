package wacc.slack.AST.literals;

import wacc.slack.AST.visitors.ASTVisitor;

public class BoolLiterAST implements LiterAST {

	private final boolean b;
	
	public BoolLiterAST(boolean b) {
		this.b = b;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public boolean getBool() {
		return b;
	}

}
