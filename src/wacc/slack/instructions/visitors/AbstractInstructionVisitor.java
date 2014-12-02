package wacc.slack.instructions.visitors;

import java.util.concurrent.Callable;

import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

/**
 * 
 * This class is meat for uses when we are interested in only a subset
 * instructions eg. Branch or Label and want to pass some fail value when it is
 * not in this subset. The fail value is provided via callable
 */
public class AbstractInstructionVisitor<T> implements InstructionVistor<T> {

	private final Callable<T> func;

	public AbstractInstructionVisitor(Callable<T> func) {
		this.func = func;

	}

	@Override
	public T visit(And and) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Orr or) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Mov mov) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Label label) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(AssemblerDirective assemblerDirective) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Swi swi) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Ldr ldr) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(BLInstruction blInsturction) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Pop pop) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Push push) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Cmp cmp) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Mul mul) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Add add) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Sub sub) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(BranchInstruction b) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T visit(Str str) {
		try {
			return func.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
