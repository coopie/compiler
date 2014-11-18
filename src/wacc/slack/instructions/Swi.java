package wacc.slack.instructions;

import wacc.slack.instructions.visitors.InstructionVistor;

//TODO: THIS CALSS IS NOT FINISHED, BUT WAS MADE TO MAKE SOME SIMPLE TESTS WORKING

public class Swi implements PseudoInstruction {

	public Swi() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(InstructionVistor<T> visitor) {
		return visitor.visit(this);
	}

}
