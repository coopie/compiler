package wacc.slack.instructions.visitors;

import wacc.slack.assemblyOperands.TemporaryRegister;

public class GenerateAssemblyBuilder {

	private IgnoringTemporariesVisitor printOperand = new IgnoringTemporariesVisitor() {
		@Override
		public String visit(TemporaryRegister temporaryRegister) {
			return "T" + temporaryRegister.getN();
		}
	};

	private int optimizationLevel = 0;

	public GenerateAssemblyBuilder ignoringTemporaries() {
		printOperand = new IgnoringTemporariesVisitor();
		return this;
	}

	public GenerateAssembly make() {
		GenerateAssembly gen = new GenerateAssembly();

		if (optimizationLevel > 0) {
			printOperand = new IgnoringTemporariesVisitor() {
				@Override
				public String visit(TemporaryRegister temporaryRegister) {
					throw new RuntimeException("found temporary register, when they should be filtered out already");
				}
			};
		}

		gen.printOperand = printOperand;
		return gen;
	}

	public GenerateAssemblyBuilder withOptimisationLevel(int i) {
		this.optimizationLevel = i;
		return this;
	}
}
