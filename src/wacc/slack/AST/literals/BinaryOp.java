package wacc.slack.AST.literals;

import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.BaseType;

public enum BinaryOp implements Liter {
	MUL, DIV, MOD, PLUS, MINUS, GT, GTE, LT, LTE, EQ, NEQ, AND, OR ;

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
			return BaseType.T_int;
		case GTE:
			return BaseType.T_int;
		case LT:
			return BaseType.T_int;
		case LTE:
			return BaseType.T_int;
		case EQ:
			return BaseType.T_bool;
		case NEQ:
			return BaseType.T_bool;
		case AND:
			return BaseType.T_bool;
		case OR:
			return BaseType.T_bool;
		default :
			return null;
		}
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
