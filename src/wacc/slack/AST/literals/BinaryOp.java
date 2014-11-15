package wacc.slack.AST.literals;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.BaseType;

public enum BinaryOp implements Liter {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR;

	public Type getType() {
		switch (this) {
		case MUL:
			return BaseType.T_int;
		case DIV:
			return BaseType.T_int;
		case MOD:
			return BaseType.T_int;
		case PLUS:
			return BaseType.T_int;
		case MINUS:
			return BaseType.T_int;
		case GT:
			return BaseType.T_bool;
		case GTE:
			return BaseType.T_bool;
		case LT:
			return BaseType.T_bool;
		case LTE:
			return BaseType.T_bool;
		case EQ:
			return BaseType.T_bool;
		case NEQ:
			return BaseType.T_bool;
		case AND:
			return BaseType.T_bool;
		case OR:
			return BaseType.T_bool;
		default:
			throw new RuntimeException("not supported BiaryOP");
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
		case MUL:
			return "*";
		case DIV:
			return "/";
		case MOD:
			return "%";
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case GT:
			return ">";
		case GTE:
			return ">=";
		case LT:
			return "<";
		case LTE:
			return "<=";
		case EQ:
			return "==";
		case NEQ:
			return "!=";
		case AND:
			return "&&";
		case OR:
			return "||";
		default:
			throw new RuntimeException("not supported BiaryOP");
		}
	}
}
