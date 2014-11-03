package wacc.slack.AST.literals;

import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class CharLiter implements Liter {

	private final String text;

	public CharLiter(String text) {
		this.text = text;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type getType() {
		return BaseType.T_char;
	}

	@Override
	public String getValue() {
		return text;
	}

}
