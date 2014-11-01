package wacc.slack.AST;

import wacc.slack.AST.visitors.ASTVisitor;

public class FuncAST implements WaccAST{	
	
	private final String ident;
	private final StatAST stat;

	public FuncAST(String ident, StatAST stat) {
		this.ident = ident;
		this.stat = stat;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public String getIdent() {
		return ident;
	}

	public StatAST getStat() {
		return stat;
	}
	
}
