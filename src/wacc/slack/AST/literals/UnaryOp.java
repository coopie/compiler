package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;

public enum UnaryOp implements Liter {
	NOT, MINUS, LEN, ORD, CHR;

	public Type getType() {
		switch (this) {
		case NOT:
			return BaseType.T_bool;
		case MINUS:
			return BaseType.T_int;
		case LEN:
			return BaseType.T_int;
		case ORD:
			return BaseType.T_int;
		case CHR:
			return BaseType.T_char;
		default:
			throw new RuntimeException("not supported UnaryOP");
		}
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public FilePosition getFilePosition() {
		return null;
	}

	@Override
	public String toString() {
		switch (this) {
		case NOT:
			return "!";
		case MINUS:
			return "-";
		case LEN:
			return "len";
		case ORD:
			return "ord";
		case CHR:
			return "chr";
		default:
			throw new RuntimeException("not suppoerted UnaryOP");
		}
	}
}
