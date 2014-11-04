package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.Type;

public abstract class BaseTypeLiter implements Liter {

	private String value;
	private FilePosition fp;
	
	public BaseTypeLiter(String value, FilePosition fp) {
		this.value = value;
		this.fp = fp;
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
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
