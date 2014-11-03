package wacc.slack.AST.types;

public enum BaseType implements Type {
	T_int, T_bool, T_char, T_string, T_pair;
	
	@Override
	public String toString() {
		return super.toString().substring(2);
	}
}
