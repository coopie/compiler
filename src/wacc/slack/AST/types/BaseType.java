package wacc.slack.AST.types;

import wacc.slack.FilePosition;

public enum BaseType implements Type {
	//NOTE: T_any is a special case and should only be used if you are CERTAIN you want
	// to use it
	T_int, T_bool, T_char;

	@Override
	public FilePosition getFilePosition() {
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString().substring(2);
	}
	
}
