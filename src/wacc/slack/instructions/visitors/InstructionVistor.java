package wacc.slack.instructions.visitors;

import wacc.slack.instructions.*;

public interface InstructionVistor<T> {

	T visit(And and);
	
	T visit(Orr or);
	
	T visit(Mov mov);
	
	T visit(Label label);

	T visit(AssemblerDirective assemblerDirective);
	
	T visit(Swi swi);

	T visit(Ldr ldr);

	T visit(BLInstruction blInsturction);
	
	T visit(Pop pop);

	T visit(Push push);

	T visit(Cmp cmp);
	
	T visit(Mul mul);
	
	T visit(Add add);

	T visit(Sub sub);

	T visit(BranchInstruction b);

	T visit(Str str);

	T visit(Eor eor);

	T visit(StrB strB);

	T visit(LdrB ldrB);

	T visit(Smull smull);


}
