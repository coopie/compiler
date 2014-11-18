package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

public class AssemblerDirective implements PseudoInstruction {

	private final String directive;

	public AssemblerDirective(String directive) {
		this.directive = directive;
		
	}
	
	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

	public String getDirective() {
		return directive;
	}
	
}