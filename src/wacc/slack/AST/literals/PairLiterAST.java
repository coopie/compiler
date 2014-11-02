package wacc.slack.AST.literals;

import wacc.slack.AST.visitors.ASTVisitor;

public class PairLiterAST implements LiterAST {
	
	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

}
