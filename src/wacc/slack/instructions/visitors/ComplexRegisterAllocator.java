package wacc.slack.instructions.visitors;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import wacc.slack.instructions.Eor;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;
import wacc.slack.interferenceGraph.RegisterMapping;

public class ComplexRegisterAllocator implements
		InstructionVistor<Deque<PseudoInstruction>> {

	private final List<ArmRegister> scratchRegisters;
	private final SwapRegisters temporarySwapper;
	private OperandVisitor<List<TemporaryRegister>> isTemporaryRegister = new OperandVisitor<List<TemporaryRegister>>(){

		@Override
		public List<TemporaryRegister> visit(ArmRegister realRegister) {
			return new LinkedList<>();
		}

		@Override
		public List<TemporaryRegister> visit(TemporaryRegister temporaryRegister) {
			return Arrays.asList(temporaryRegister);
		}

		@Override
		public List<TemporaryRegister> visit(Label label) {
			return new LinkedList<>();
		}

		@Override
		public List<TemporaryRegister> visit(ImmediateValue immediateValue) {
			return new LinkedList<>();
		}

		@Override
		public List<TemporaryRegister> visit(Address address) {
			List<TemporaryRegister> l = new LinkedList<>();
			
			l.addAll(address.getRegister().accept(this));
			if(address.getRegOffset() != null) {
				l.addAll(address.getRegOffset().accept(this));
			}
			
			return l;
		}

		@Override
		public List<TemporaryRegister> visit(NoOperand noOperand) {
			return new LinkedList<>();
		}

	};

	private final class SwapRegisters implements OperandVisitor<Operand> {
		private final RegisterMapping mapping;
		
		public SwapRegisters(RegisterMapping m) {
			this.mapping = m;
		}
		
		@Override
		public Operand visit(ArmRegister realRegister) {
			return realRegister;
		}

		@Override
		public Operand visit(TemporaryRegister temporaryRegister) {
			return mapping.getRegisterSwap(temporaryRegister);
		}

		@Override
		public Operand visit(Label label) {
			return label;
		}

		@Override
		public Operand visit(ImmediateValue immediateValue) {
			return immediateValue;
		}

		@Override
		public Operand visit(Address address) {
			Register r = (Register)address.getRegister().accept(this);
			if(address.getOffset() != null) {
				return new Address(r,address.getOffset());
			} else if (address.getRegOffset() != null) {
				Register o = (Register)address.getRegOffset().accept(this);
				return new Address(r,o);
			} else {
				throw new RuntimeException("address neither a integer offset or register offset");
			}
		}

		@Override
		public Operand visit(NoOperand noOperand) {
			return noOperand;
		}
	}
	
	private interface InsturctionGenerator {
		PseudoInstruction make(Operand dest, Operand source, Operand source2);
	}

	public ComplexRegisterAllocator(RegisterMapping mapping, List<ArmRegister> scratchRegister) {
		scratchRegisters = scratchRegister;
		this.temporarySwapper = new SwapRegisters(mapping);
	}
	
	public ComplexRegisterAllocator(RegisterMapping mapping) {
		this(mapping,Arrays.asList(ArmRegister.r7,ArmRegister.r8,ArmRegister.r9));
	}
	
	public ComplexRegisterAllocator() {
		this(new RegisterMapping(){
			@Override
			public Register getRegisterSwap(Register r) {
				return r;
			}	
		});
	}
	
	
	
	
	
	
	
	
	@Override
	public Deque<PseudoInstruction> visit(final And and) {		
		return trinaryInstructionSwap(and.getDest(), and.getSource(), and.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new And(dest,source,source2,and.getCond());
			}
			
		});
	}
	
	

	@Override
	public Deque<PseudoInstruction> visit(final Orr or) {
		return trinaryInstructionSwap(or.getDest(), or.getSource(), or.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new Orr(dest,source,source2,or.getCond());
			}
			
		});
	}

	@Override
	public Deque<PseudoInstruction> visit(Mov mov) {
		Operand dest = mov.getDest();
		Operand source = mov.getSource();
		
		dest = dest.accept(temporarySwapper);
		source = source.accept(temporarySwapper);
		
		Deque<PseudoInstruction> l = new LinkedList<>();
		Deque<PseudoInstruction> s = new LinkedList<>();
	
		source = loadIfSpilled(l, source, new LinkedList<>(scratchRegisters));
		dest = storeIfSpilled(s, dest, new LinkedList<>(scratchRegisters));
		
		l.add(new Mov(dest,source, mov.getCond()));
		l.addAll(s);
		
		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Label label) {
		return listify(label);
	}

	@Override
	public Deque<PseudoInstruction> visit(AssemblerDirective assemblerDirective) {
		return listify(assemblerDirective);
	}

	@Override
	public Deque<PseudoInstruction> visit(Swi swi) {
		return listify(swi);
	}

	@Override
	public Deque<PseudoInstruction> visit(Ldr ldr) {
		Operand dest = ldr.getDest();
		Operand source = ldr.getSource();
		
		dest = dest.accept(temporarySwapper);
		source = source.accept(temporarySwapper);
		
		Deque<PseudoInstruction> l = new LinkedList<>();
		Deque<PseudoInstruction> s = new LinkedList<>();
	
		source = loadIfSpilled(l, source, new LinkedList<>(scratchRegisters));
		dest = storeIfSpilled(s, dest, new LinkedList<>(scratchRegisters));
		
		l.add(new Ldr(dest,source, ldr.getCond()));
		l.addAll(s);
		
		return l;
	}


	@Override
	public Deque<PseudoInstruction> visit(BLInstruction blInsturction) {
		return listify(blInsturction);
	}

	@Override
	public Deque<PseudoInstruction> visit(Pop pop) {
		Operand o = pop.getOperand();
		Deque<PseudoInstruction> s = new LinkedList<>();
		
		o = o.accept(temporarySwapper);
		o = storeIfSpilled(s, o, new LinkedList<>(scratchRegisters));
		
		s.add(new Pop(o));
		s.addAll(s);
		
		return s;
	}

	@Override
	public Deque<PseudoInstruction> visit(Push push) {
		Operand o = push.getOperand();
		Deque<PseudoInstruction> l = new LinkedList<>();
		
		o = o.accept(temporarySwapper);
		o = loadIfSpilled(l, o, new LinkedList<>(scratchRegisters));
		
		l.add(new Push(o));
		
		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(Cmp cmp) {
		Operand dest = cmp.getDest();
		Operand source = cmp.getSource();
		
		dest = dest.accept(temporarySwapper);
		source = source.accept(temporarySwapper);
		
		Deque<PseudoInstruction> l = new LinkedList<>();
		
		List<ArmRegister> scratch = new LinkedList<>(scratchRegisters);
		
		source = loadIfSpilled(l, source, scratch);
		dest = loadIfSpilled(l, dest,scratch);
		
		l.add(new Cmp(source,dest, cmp.getCond()));
		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(final Mul mul) {
		return trinaryInstructionSwap(mul.getDest(), mul.getSource(), mul.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new Mul(dest,source,source2,mul.getCond());
			}
			
		});
	}

	@Override
	public Deque<PseudoInstruction> visit(final Add add) {
		return trinaryInstructionSwap(add.getDest(), add.getSource(), add.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new Add(dest,source,source2,add.getCond());
			}
			
		});
	}

	@Override
	public Deque<PseudoInstruction> visit(final Sub sub) {
		return trinaryInstructionSwap(sub.getDest(), sub.getSource(), sub.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new Sub(dest,source,source2,sub.getCond());
			}
			
		});
	}

	@Override
	public Deque<PseudoInstruction> visit(BranchInstruction b) {
		return listify(b);
	}

	@Override
	public Deque<PseudoInstruction> visit(Str str) {
		Operand dest = str.getDest();
		Operand source = str.getSource();
		
		dest = dest.accept(temporarySwapper);
		source = source.accept(temporarySwapper);
		
		Deque<PseudoInstruction> l = new LinkedList<>();
		
		List<ArmRegister> scratch = new LinkedList<>(scratchRegisters);
		
		source = loadIfSpilled(l, source, scratch);
		dest = loadIfSpilled(l, dest,scratch);
		
		l.add(new Str(source,dest, str.getCond()));
		return l;
	}

	@Override
	public Deque<PseudoInstruction> visit(final Eor eor) {
		return trinaryInstructionSwap(eor.getDest(), eor.getSource(), eor.getSource2(), new InsturctionGenerator() {
			@Override
			public PseudoInstruction make(Operand dest, Operand source,
					Operand source2) {
				return new Eor(dest,source,source2,eor.getCond());
			}
			
		});
	}

	
	private Deque<PseudoInstruction> listify(PseudoInstruction... i) {
		return new LinkedList<PseudoInstruction>(Arrays.asList(i));
	}

	private Deque<PseudoInstruction> trinaryInstructionSwap(Operand dest, Operand source, Operand source2,  InsturctionGenerator gen) {
		
		dest = dest.accept(temporarySwapper);
		source = source.accept(temporarySwapper);
		source2 = source2.accept(temporarySwapper);
		
		Deque<PseudoInstruction> l = new LinkedList<>();
		Deque<PseudoInstruction> s = new LinkedList<>();
		
		List<ArmRegister> scratch = new LinkedList<>(scratchRegisters);
		
		source = loadIfSpilled(l, source, scratch);
		source2 = loadIfSpilled(l, source2,scratch);
		dest = storeIfSpilled(s, dest,scratch);

		l.add(gen.make(dest,source,source2));
		l.addAll(s);
		
		return l;
	}

	private Operand storeIfSpilled(Deque<PseudoInstruction> s, Operand dest, List<ArmRegister> scratchRegs) {
		List<TemporaryRegister> spilledRegisters = dest.accept(isTemporaryRegister);
	
		if(!spilledRegisters.isEmpty()) {
			Iterator<ArmRegister> i = scratchRegs.iterator();
			final Map<Register, ArmRegister> map = new HashMap<>();
			for(TemporaryRegister t : spilledRegisters) {
				if(i.hasNext()) {
					ArmRegister scratchReg = i.next();
					map.put(t, scratchReg);
					s.add(new Ldr(scratchReg, new Address(ArmRegister.sp, t.getN())));
				} else {
					throw new RuntimeException("not enough scracth registers for operand " + dest);
				}
			}
			
			for(ArmRegister r : map.values()) {
				scratchRegs.remove(r);
			}
			
			return dest.accept(new SwapRegisters(new RegisterMapping() {
				@Override
				public Register getRegisterSwap(Register r) {
					if(map.containsKey(r)) {
						return map.get(r);
					} else {
						throw new RuntimeException("something went horribly wrong");
					}
				}}));
		} else {
			return dest;
		}
	}

	private Operand loadIfSpilled(Deque<PseudoInstruction> l, Operand source, List<ArmRegister> scratchRegs) {
		List<TemporaryRegister> spilledRegisters = source.accept(isTemporaryRegister);
	
		if(!spilledRegisters.isEmpty()) {
			Iterator<ArmRegister> i = scratchRegs.iterator();
			final Map<Register, ArmRegister> map = new HashMap<>();
			for(TemporaryRegister t : spilledRegisters) {
				if(i.hasNext()) {
					ArmRegister scratchReg = i.next();
					map.put(t, scratchReg);
					l.add(new Ldr(scratchReg, new Address(ArmRegister.sp, t.getN())));
				} else {
					throw new RuntimeException("not enough scracth registers for operand " + source);
				}
			}
			
			for(ArmRegister r : map.values()) {
				scratchRegs.remove(r);
			}
			
			return source.accept(new SwapRegisters(new RegisterMapping() {
				@Override
				public Register getRegisterSwap(Register r) {
					if(map.containsKey(r)) {
						return map.get(r);
					} else {
						throw new RuntimeException("something went horribly wrong");
					}
				}}));
		} else {
			return source;
		}
	}
}
