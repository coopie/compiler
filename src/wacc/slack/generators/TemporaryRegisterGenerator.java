package wacc.slack.generators;

import wacc.slack.assemblyOperands.FutureValue;
import wacc.slack.assemblyOperands.TemporaryRegister;

// Temporary Register Generator
public class TemporaryRegisterGenerator implements FutureValue<String> {

	private int n = 1;
	
	public TemporaryRegister generate(int weight) {
		return new TemporaryRegister(n++,weight);
	}

	@Override
	public String getValue() {
		int stackSpace = (n - 1)*4;
		//8 allign stacks
		if(stackSpace % 8 == 0)
			return Integer.toString(stackSpace);
		else
			return Integer.toString(stackSpace + 4);
		
	}
}
