package wacc.slack.AST.types;

public enum BaseType implements Type {
	T_int, T_bool, T_char, T_string, T_pair;

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
	public String toString() {
		return super.toString().substring(2);
	}
}
