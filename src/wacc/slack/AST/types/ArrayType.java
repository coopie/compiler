package wacc.slack.AST.types;

public class ArrayType implements Type {

	Type type;
	
	ArrayType(Type type) {
		this.type = type;
	}
}
