package wacc.slack.instructions.visitors;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.Operand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Eor;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.LdrB;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.StrB;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

// TODO: Factor out code in STR and STRB and likewise for LTR and LTRB
// only treats all temporary registers as load/store before the command they are used
public class SimpleRegisterAllocator implements
		InstructionVistor<Deque<PseudoInstruction>> {

	// currently we need 3 registers, these registers may well need to be
	// changed to combat problems with
	// other uses

	// assumed destination
	private final Register RA = ArmRegister.r7;
	// assumed source1
	private final Register RB = ArmRegister.r8;
	// assumed source2
	private final Register RC = ArmRegister.r9;

	@Override
	public Deque<PseudoInstruction> visit(And and) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, and.getSource(),
				and.getSource2(), and.getDest(), and.getCond());
		l.add(new And(r.dest, r.source, r.source2, and.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Orr or) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, or.getSource(),
				or.getSource2(), or.getDest(), or.getCond());
		l.add(new Orr(r.dest, r.source, r.source2, or.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Mov mov) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapBinaryInstruction(l, mov.getSource(), mov.getDest(),
				mov.getCond());
		l.add(new Mov(r.dest, r.source, mov.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Label label) {
		return new LinkedList<>(Arrays.asList((PseudoInstruction) label));
	}

	@Override
	public Deque<PseudoInstruction> visit(AssemblerDirective assemblerDirective) {
		return new LinkedList<>(
				Arrays.asList((PseudoInstruction) assemblerDirective));
	}

	@Override
	public Deque<PseudoInstruction> visit(Swi swi) {
		return new LinkedList<>(Arrays.asList((PseudoInstruction) swi));
	}

	@Override
	public Deque<PseudoInstruction> visit(Ldr ldr) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapBinaryInstruction(l, ldr.getSource(), ldr.getDest(),
				ldr.getCond());

		if (r.source instanceof ImmediateValue || r.source instanceof Address) {
			l.add(new Ldr(r.dest, r.source, ldr.getCond()));
		} else if (r.source instanceof Register && r.source2 == null
				&& !(ldr.getSource() instanceof Address)) {
			l.add(new Ldr(r.dest, new Address((Register) r.source), ldr
					.getCond()));
		} else if (r.source instanceof Register && r.source2 != null) {
			l.add(new Ldr(r.dest, new Address((Register) r.source,
					(Register) r.source2), ldr.getCond()));
		} else if (r.source instanceof Register
				&& ldr.getSource() instanceof Address) {
			l.add(new Ldr(r.dest, new Address((Register) r.source,
					((Address) ldr.getSource()).getOffset()), ldr.getCond()));
		} else {
			throw new RuntimeException(
					"Ldr error in simple register allocator.");
		}

		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(BLInstruction blInsturction) {
		return new LinkedList<>(
				Arrays.asList((PseudoInstruction) blInsturction));
	}

	@Override
	public Deque<PseudoInstruction> visit(Pop pop) {
		return new LinkedList<>(Arrays.asList((PseudoInstruction) pop));
	}

	@Override
	public Deque<PseudoInstruction> visit(Push push) {
		return new LinkedList<>(Arrays.asList((PseudoInstruction) push));
	}

	@Override
	public Deque<PseudoInstruction> visit(Cmp cmp) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		Operand s1 = cmp.getDest();
		Operand s2 = cmp.getSource();

		if (loadSourceReg1IfNeccessary(l, s1, RB, cmp.getCond()) > 0) {
			s1 = RB;
		}

		if (loadSourceReg1IfNeccessary(l, s2, RC, cmp.getCond()) > 0) {
			s2 = RC;
		}

		l.add(new Cmp(s1, s2, cmp.getCond()));

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Mul mul) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, mul.getSource(),
				mul.getSource2(), mul.getDest(), mul.getCond());
		l.add(new Mul(r.dest, r.source, r.source2, mul.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Add add) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, add.getSource(),
				add.getSource2(), add.getDest(), add.getCond());
		l.add(new Add(r.dest, r.source, r.source2, add.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Sub sub) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, sub.getSource(),
				sub.getSource2(), sub.getDest(), sub.getCond());
		l.add(new Sub(r.dest, r.source, r.source2, sub.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(BranchInstruction b) {
		return new LinkedList<>(Arrays.asList((PseudoInstruction) b));
	}

	@Override
	public Deque<PseudoInstruction> visit(Str str) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		Operand s1 = str.getDest();
		Operand s2 = str.getSource();
		Operand s3 = null;

		if (loadSourceReg1IfNeccessary(l, s1, RB, str.getCond()) > 0) {
			s1 = RB;
		}

		if (loadSourceReg1IfNeccessary(l, s2, RC, str.getCond()) > 0) {
			s2 = RC;
		}

		if (returnTempRegOffset != null
				&& loadSourceReg1IfNeccessary(l, returnTempRegOffset, RA,
						str.getCond()) > 0) {
			s3 = RA;
			l.add(new Str(s2, new Address((Register) s1, (Register) s3), str
					.getCond()));
			returnTempRegOffset = null;
			return l;
		}
		returnTempRegOffset = null;

		if (s1 instanceof Register) {
			l.add(new Str(s2, new Address((Register) s1, ((Address) str
					.getDest()).getOffset()), str.getCond()));
		} else if (s1 instanceof Address) {
			l.add(new Str(s2, s1, str.getCond()));
		} else {
			throw new RuntimeException(
					"Str error in simple register allocator.");
		}
		return l;
	}

	// Returns negative value if it hasn't done anything
	private int storeDestRegIfNeccessary(Deque<PseudoInstruction> l,
			Operand destReg, Condition cond) {
		int n = temporaryNumber(destReg);
		if (n > 0) {
			l.add(new Str(RA, new Address(ArmRegister.sp, n * 4), cond));
		}
		return n;
	}

	// Returns negative value if it hasn't done anything
	private int loadSourceReg1IfNeccessary(Deque<PseudoInstruction> l,
			Operand sourceReg, Register r, Condition cond) {
		int n = temporaryNumber(sourceReg);
		if (n > 0) {
			l.add(new Ldr(r, new Address(ArmRegister.sp, n * 4), cond));
		}
		return n;
	}

	private SwapReturn swapTrinaryInstruction(Deque<PseudoInstruction> l,
			Operand source, Operand source2, Operand dest, Condition cond) {

		Deque<PseudoInstruction> destStore = new LinkedList<>();

		if (loadSourceReg1IfNeccessary(l, source, RB, cond) > 0) {
			source = RB;
		}

		if (loadSourceReg1IfNeccessary(l, source2, RC, cond) > 0) {
			source2 = RC;
		}

		if (storeDestRegIfNeccessary(destStore, dest, cond) > 0) {
			dest = RA;
		}

		returnTempRegOffset = null;
		return new SwapReturn(destStore, dest, source, source2);
	}

	private SwapReturn swapBinaryInstruction(Deque<PseudoInstruction> l,
			Operand source, Operand dest, Condition cond) {

		Deque<PseudoInstruction> destStore = new LinkedList<>();

		if (loadSourceReg1IfNeccessary(l, source, RB, cond) > 0) {
			source = RB;
		}

		if (storeDestRegIfNeccessary(destStore, dest, cond) > 0) {
			dest = RA;
		}

		SwapReturn swapReturn = new SwapReturn(destStore, dest, source);
		if (returnTempRegOffset != null
				&& loadSourceReg1IfNeccessary(l, returnTempRegOffset, RC, cond) > 0) {
			swapReturn = new SwapReturn(destStore, dest, source, RC);
		}

		returnTempRegOffset = null;

		return swapReturn;
	}

	static Register returnTempRegOffset = null;

	// -1 represents not temporary register
	private int temporaryNumber(Operand r) {
		if (r == null)
			return -1;

		return (r.accept(new OperandVisitor<Integer>() {

			@Override
			public Integer visit(ArmRegister realRegister) {
				return -1;
			}

			@Override
			public Integer visit(TemporaryRegister temporaryRegister) {
				return temporaryRegister.getN();
			}

			@Override
			public Integer visit(Label label) {
				return -1;
			}

			@Override
			public Integer visit(ImmediateValue immediateValue) {
				return -1;
			}

			@Override
			public Integer visit(Address address) {
				// TODO: won't work with register offest
				if (address.getRegOffset() != null) {
					SimpleRegisterAllocator.returnTempRegOffset = address
							.getRegOffset();
				}
				return address.getRegister().accept(this);
			}

			@Override
			public Integer visit(NoOperand noOperand) {
				return -1;
			}

		}));
	}

	class SwapReturn {
		public Deque<PseudoInstruction> destStore;
		public Operand source;
		public Operand dest;
		public Operand source2 = null;

		public SwapReturn(Deque<PseudoInstruction> destStore, Operand dest,
				Operand source) {
			this.destStore = destStore;
			this.source = source;
			this.dest = dest;
		}

		public SwapReturn(Deque<PseudoInstruction> destStore, Operand dest,
				Operand source, Operand source2) {
			this.source2 = source2;
			this.destStore = destStore;
			this.source = source;
			this.dest = dest;
		}
	}

	@Override
	public Deque<PseudoInstruction> visit(Eor eor) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapTrinaryInstruction(l, eor.getSource(),
				eor.getSource2(), eor.getDest(), eor.getCond());
		l.add(new Eor(r.dest, r.source, r.source2, eor.getCond()));
		l.addAll(r.destStore);

		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(StrB strB) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		Operand s1 = strB.getDest();
		Operand s2 = strB.getSource();
		Operand s3 = null;

		if (loadSourceReg1IfNeccessary(l, s1, RB, strB.getCond()) > 0) {
			s1 = RB;
		}

		if (loadSourceReg1IfNeccessary(l, s2, RC, strB.getCond()) > 0) {
			s2 = RC;
		}

		if (returnTempRegOffset != null
				&& loadSourceReg1IfNeccessary(l, returnTempRegOffset, RA,
						strB.getCond()) > 0) {
			s3 = RA;
			l.add(new Str(s2, new Address((Register) s1, (Register) s3), strB
					.getCond()));
			returnTempRegOffset = null;
			return l;
		}
		returnTempRegOffset = null;

		if (s1 instanceof Register) {
			l.add(new Str(s2, new Address((Register) s1, ((Address) strB
					.getDest()).getOffset()), strB.getCond()));
		} else if (s1 instanceof Address) {
			l.add(new Str(s2, s1, strB.getCond()));
		} else {
			throw new RuntimeException(
					"Strb error in simple register allocator.");
		}
		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(LdrB ldrB) {
		Deque<PseudoInstruction> l = new LinkedList<>();

		SwapReturn r = swapBinaryInstruction(l, ldrB.getSource(), ldrB.getDest(),
				ldrB.getCond());

		if (r.source instanceof ImmediateValue || r.source instanceof Address) {
			l.add(new Ldr(r.dest, r.source, ldrB.getCond()));
		} else if (r.source instanceof Register && r.source2 == null
				&& !(ldrB.getSource() instanceof Address)) {
			l.add(new Ldr(r.dest, new Address((Register) r.source), ldrB
					.getCond()));
		} else if (r.source instanceof Register && r.source2 != null) {
			l.add(new Ldr(r.dest, new Address((Register) r.source,
					(Register) r.source2), ldrB.getCond()));
		} else if (r.source instanceof Register
				&& ldrB.getSource() instanceof Address) {
			l.add(new Ldr(r.dest, new Address((Register) r.source,
					((Address) ldrB.getSource()).getOffset()), ldrB.getCond()));
		} else {
			throw new RuntimeException(
					"Ldr error in simple register allocator.");
		}

		l.addAll(r.destStore);

		return l;
	}
}
