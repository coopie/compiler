package wacc.slack.AST.types;

import wacc.slack.AST.visitors.ASTVisitor;

public enum BaseType implements Type {
	T_int, T_bool, T_char, T_string, T_pair;

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
