package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public enum BaseType implements Type {
	T_int, T_bool, T_char, T_string, T_pair;

	@Override
	public FilePosition getFilePosition() {
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString().substring(2);
	}
}
