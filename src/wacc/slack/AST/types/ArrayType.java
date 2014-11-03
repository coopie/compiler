package wacc.slack.AST.types;

import wacc.slack.AST.visitors.ASTVisitor;

public class ArrayType implements Type {

	Type type;
	
	ArrayType(Type type) {
		this.type = type;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	public boolean equals(Type t) {
		return t instanceof ArrayType && type.equals(((ArrayType) t).type); 
	}
}
