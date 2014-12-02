package wacc.slack.assemblyOperands;

import wacc.slack.generators.TemporaryRegisterGenerator;

public class ImmediateValue implements Operand {

	private final String value;
	private final FutureValue<String> trg;
	
	public ImmediateValue(String value) {
		this.value = "=" + value;
		trg = null;
	}
	
	public ImmediateValue(int n) {
		this.value = "" + n;
		trg = null;
	}

	
	public ImmediateValue(FutureValue<String> trg) {
		this.trg = trg;
		value = null;
	}

	public String getValue(String prefix) {
		if(trg != null) return "#" + trg.getValue();
		
		if (!value.startsWith("=")) {
			return prefix + value;
		}
		return value;
	}

	@Override
	public <T> T accept(OperandVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
