package wacc.slack.AST.symbolTable;

import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.types.Type;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.TemporaryRegisterGenerator;

public class IdentInfo {

	private final Type ident_type;
	private final FilePosition declaredAt;
	private TemporaryRegister register;

	public IdentInfo(Type ident_type, FilePosition declaredAt) {
		this.ident_type = ident_type;
		this.declaredAt = declaredAt;
		this.register = null;
	}

	public Type getType() {
		return ident_type;
	}

	public FilePosition getDeclaredAt() {
		return declaredAt;
	}

	public List<Type> getParamTypes() {
		throw new RuntimeException(
				"can't get params on non function identifier");
	}

	public TemporaryRegister getTemporaryRegister() {
		return register;
	}
	
	
	public TemporaryRegister getTemporaryRegister(TemporaryRegisterGenerator trg, int weight) {
		if(register == null) {
			this.register = trg.generate(weight);
		}
		return register;
	}
	

	public void setTemporaryRegister(TemporaryRegister r) {
		this.register = r;
	}
	
	@Override
	public String toString() {
		return "[" + ident_type + ", " + declaredAt + ", " + register + "]"; 
	}
	
	
	
}
