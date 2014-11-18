package wacc.slack.instructions.visitors;

import wacc.slack.instructions.*;

public interface InstructionVistor<T> {

	T visit(Mov mov);

	T visit(Label label);

	T visit(AssemblerDirective assemblerDirective);
	
	T visit(Swi swi);

	T visit(Ldr ldr);

	T visit(BLInstruction blInsturction);

	T visit(Cmp cmp);

}
