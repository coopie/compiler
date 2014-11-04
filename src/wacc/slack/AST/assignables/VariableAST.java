package wacc.slack.AST.assignables;

import wacc.slack.AST.visitors.ASTVisitor;

public class VariableAST implements Assignable {

	private final String id;

	public VariableAST(String id) {
		this.id = id;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkType() {
		// TODO Auto-generated method stub

	}

}
