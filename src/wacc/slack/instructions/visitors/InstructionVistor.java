package wacc.slack.instructions.visitors;

import wacc.slack.instructions.*;

public interface InstructionVistor<T> {

	T accept(Mov mov);

	T visit(Swi swi);

	T accept(Label label);

}
