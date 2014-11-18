package wacc.slack.instructions.visitors;

import wacc.slack.instructions.*;

public interface InstructionVistor<T> {

	T accept(Mov mov);

	T visit(Label label);

	T accept(Label label);

	T visit(AssemblerDirective assemblerDirective);
	
	T visit(Swi swi);

	T visit(Ldr ldr);

}
