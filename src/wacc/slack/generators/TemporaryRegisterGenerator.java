package wacc.slack.generators;

import wacc.slack.assemblyOperands.TemporaryRegister;

// Temporary Register Generator
public class TemporaryRegisterGenerator {

	private int n = 1;
	
	public TemporaryRegister generate() {
		return new TemporaryRegister(n++);
	}
}
