package wacc.slack.AST.symbolTable;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.assignables.Param;
import wacc.slack.AST.types.Type;

public class FuncIdentInfo extends IdentInfo {

	private final List<Type> paramTypes;

	public FuncIdentInfo(Type ident_type, List<Type> paramTypes,
			FilePosition declaredAt) {
		super(ident_type, declaredAt);
		this.paramTypes = paramTypes;
	}
	@Override
	public List<Type> getParamTypes() {
		return paramTypes;
	}

}
