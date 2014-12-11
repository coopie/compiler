package wacc.slack.AST.visitors;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArrayElemAST;
import wacc.slack.AST.assignables.Assignable;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.assignables.NewPairAST;
import wacc.slack.AST.assignables.Param;
import wacc.slack.AST.assignables.SndAST;
import wacc.slack.AST.assignables.VariableAST;
import wacc.slack.AST.literals.ArrayLiterAST;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.statements.AssignStatAST;
import wacc.slack.AST.statements.BeginEndAST;
import wacc.slack.AST.statements.ExitStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.IfWithoutElseStatement;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.Operand;
import wacc.slack.assemblyOperands.Operand2;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.ControlFlowLabelGenerator;
import wacc.slack.generators.LiteralLabelGenerator;
import wacc.slack.generators.TemporaryRegisterGenerator;
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
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Smull;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.StrB;
import wacc.slack.instructions.Sub;
import wacc.slack.interferenceGraph.CompileProgramAST;

// NB: use LinkedList 

public class IntermediateCodeGenerator implements
		ASTVisitor<Deque<PseudoInstruction>> {

	public static final String STRING_FORMAT_LABEL = "string_format";
	public static final String PRINT_CHAR_FORMAT_LABEL = "print_char_format";
	public static final String SCAN_CHAR_FORMAT_LABEL = "scan_char_format";

	public static final String NEW_LINE_CHAR = "new_line_char";
	public static final String INT_FORMAT_LABEL = "int_format";
	public static final String INT_SCANF_STORE_LABEL = "intScanfStoreLabel";
	public static final String ADDRESS_FORMAT_LABEL = "address_format";
	public static final Label TRUE_LABEL = new Label("l_true");
	public static final Label FALSE_LABEL = new Label("l_false");

	// To print error messages, get a printstatementast.accept and add it to
	// instrListSpilledRegistersHaveTheRestOfTheRegistersInThem
	public static final PrintStatementAST NEGATIVE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: negative index.\"", null),
					null), null);

	public static final PrintStatementAST DIVIDE_BY_ZERO_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"DivideByZeroError: expression has divisor of zero .\"",
					null), null), null);

	public static final PrintStatementAST LARGE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: index too large.\"", null),
					null), null);

	public static final PrintStatementAST NULL_REFERENCE_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"NullReferenceError: dereference a null reference.\"",
					null), null), null);

	public static final PrintStatementAST INTEGER_ERROR = new PrintStatementAST(
			new ValueExprAST(
					new StringLiter(
							"\"IntegerValueException: the value of an integer is too large or too small.\"",
							null), null), null);

	public final class DefaultOperandVisitor implements OperandVisitor<Operand> {

		@Override
		public Operand visit(ArmRegister realRegister) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(TemporaryRegister temporaryRegister) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(Label label) {
			return new ImmediateValue(label.getName());
		}

		@Override
		public Operand visit(ImmediateValue immediateValue) {
			return immediateValue;
		}

		@Override
		public Operand visit(Address address) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(NoOperand noOperand) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(Operand2 operand2) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/*
	 * private Deque<PseudoInstruction> textSection = new LinkedList<>();
	 * private Deque<PseudoInstruction> dataSection = new LinkedList<>();
	 * private Deque<PseudoInstruction> compilerDefinedFunctions = new
	 * LinkedList<>();
	 */

	private Register returnedOperand = null;

	/*
	 * contains the current weight of registers for current scope, use everytime
	 * you create a register
	 */
	private int weight = 0;
	private TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();

	@Override
	public Deque<PseudoInstruction> visit(ProgramAST prog) {
		throw new RuntimeException(
				"there shouldn't be a program inside a program, ProgramAST should only  be visited in CompileProgramAST");
	}

	/*
	 * Stack at call | param 3 | ... | param 2 | [lr + 8] | param 1 | [lr + 4]
	 * ArmRegister.lr->| caller pc | [lr] | saved reg | [lr - 4] | saved reg |
	 * ... | saved reg | [sp]
	 */
	@Override
	public Deque<PseudoInstruction> visit(FuncAST func) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		// frame needs to be offset by one because lr is stored there on the
		// stack
		int frameOffset = 1;
		TemporaryRegister temporary;
		for (Param p : func.getParamList()) {
			temporary = trg.generate(weight);
			instrList.add(new Ldr(temporary, new Address(ArmRegister.lr,
					frameOffset * 4)));
			p.getScope().lookup(p.getIdent()).setTemporaryRegister(temporary);
			frameOffset++;
		}
		instrList.addAll(func.getStat().accept(this));
		// this is done by return
		// instrList.add(new Mov(ArmRegister.r0, returnedOperand));

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(StatListAST statAST) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		for (WaccAST stat : statAST.getChildren()) {
			instrList.addAll(stat.accept(this));
		}
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(AssignStatAST assignStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(assignStat.getRhs().accept(this));
		Register rhsReg = returnedOperand;

		Register varReg;
		if (assignStat
				.getLhs()
				.getScope()
				.lookup(assignStat.getLhs().getName(),
						assignStat.getFilePosition()).getTemporaryRegister() != null) {
			varReg = assignStat
					.getLhs()
					.getScope()
					.lookup(assignStat.getLhs().getName(),
							assignStat.getFilePosition())
					.getTemporaryRegister();
		} else {
			varReg = trg.generate(weight);
		}

		// Set the variable identinfo to store this temp reg
		if (!(varReg instanceof TemporaryRegister)) {
			throw new RuntimeException(
					"Variable assignRHS should never be put in a real register, "
							+ varReg);
		}

		if (assignStat.getLhs() instanceof ArrayElemAST) {
			weight = weight * 5;
			Register typeSizeReg = trg.generate(weight);
			int typeSize = 4;
			if (((ArrayElemAST) assignStat.getLhs()).getType().equals(
					BaseType.T_bool)
					|| ((ArrayElemAST) assignStat.getLhs()).getType().equals(
							BaseType.T_char)) {
				typeSize = 1;
			}
			instrList.add(new Mov(typeSizeReg, new ImmediateValue(typeSize)));

			Register arrayReg = assignStat.getLhs().getScope()
					.lookup(assignStat.getLhs().getName())
					.getTemporaryRegister();
			Register backUpArray = trg.generate(weight);
			instrList.add(new Mov(backUpArray, arrayReg));

			Register index = trg.generate(weight);
			for (int i = 0; i < ((ArrayElemAST) assignStat.getLhs()).getExprs()
					.size(); i++) {
				instrList.addAll(((ArrayElemAST) assignStat.getLhs())
						.getExprs().get(i).accept(this));

				// Multiply the index by the size of the array
				instrList.add(new Mul(index, returnedOperand, typeSizeReg));

				// Add 4 since the first element of every array is the size
				// which is
				// always 4 bytes
				instrList.add(new Add(index, new ImmediateValue(4)));

				if (i == ((ArrayElemAST) assignStat.getLhs()).getExprs().size() - 1) {
					if (typeSize == 4) {
						instrList.add(new Str(rhsReg, new Address(arrayReg,
								index)));
					} else {
						instrList.add(new StrB(rhsReg, new Address(arrayReg,
								index)));
					}

				} else {
					// Load array at index. These will always be address so 4
					// bytes
					instrList.add(new Ldr(arrayReg,
							new Address(arrayReg, index)));

				}
			}

			// Move array back into original register
			instrList.add(new Mov(arrayReg, backUpArray));
			weight = weight * 5;
		} else if (assignStat.getLhs() instanceof FstAST) {
			FstAST fst = (FstAST) assignStat.getLhs();
			Register ret = fst.getScope().lookup(fst.getName())
					.getTemporaryRegister();

			// Check for null pointers
			instrList.add(new Mov(ArmRegister.r0, ret));
			instrList.add(new BLInstruction("p_check_null_pointer"));

			instrList.add(new Str(rhsReg, new Address(ret)));
		} else if (assignStat.getLhs() instanceof SndAST) {
			SndAST snd = (SndAST) assignStat.getLhs();
			Register ret = snd.getScope().lookup(snd.getName())
					.getTemporaryRegister();

			// Check for null pointers
			instrList.add(new Mov(ArmRegister.r0, ret));
			instrList.add(new BLInstruction("p_check_null_pointer"));

			instrList.add(new Str(rhsReg, new Address(ret, 4)));
		} else {
			// Assigning variables
			instrList.add(new Mov(varReg, rhsReg));
			assignStat
					.getLhs()
					.getScope()
					.lookup(assignStat.getLhs().getName(),
							assignStat.getLhs().getFilePosition())
					.setTemporaryRegister((TemporaryRegister) varReg);
		}

		returnedOperand = varReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(BeginEndAST beginEnd) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.addAll(beginEnd.getBody().accept(this));
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(IfStatementAST ifStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		Label falsel = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		Label endl = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());

		instrList.addAll(ifStat.getCond().accept(this));
		instrList.add(new Cmp(returnedOperand, new ImmediateValue(0)));
		instrList.add(new BranchInstruction(Condition.EQ, falsel));

		weight = weight / 2;
		instrList.addAll(ifStat.getTrueStats().accept(this));
		instrList.add(new BranchInstruction(Condition.AL, endl));
		instrList.add(falsel);
		instrList.addAll(ifStat.getFalseStats().accept(this));
		instrList.add(endl);

		weight = (weight + 1) * 2;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(SkipStatementAST skipStat) {
		return new LinkedList<PseudoInstruction>();
	}

	@Override
	public Deque<PseudoInstruction> visit(WhileStatementAST whileStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		weight = weight * 10;

		Label end = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		instrList.add(new BranchInstruction(Condition.AL, end));

		Label start = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		instrList.add(start);
		instrList.addAll(whileStat.getBody().accept(this));

		instrList.add(end);
		instrList.addAll(whileStat.getCond().accept(this));

		instrList.add(new Cmp(returnedOperand, new ImmediateValue(1)));
		instrList.add(new BranchInstruction(Condition.EQ, start));
		weight = weight / 10;

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReturnStatementAST exprStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.addAll(exprStat.getExpr().accept(this));
		instrList.add(new Mov(ArmRegister.r0, returnedOperand));
		instrList.add(new BranchInstruction(Condition.AL, new Label(exprStat
				.getFunction() + "_end")));

		returnedOperand = null;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(PrintlnStatementAST printlnStat) {
		ExprAST expr = printlnStat.getExpr();
		Deque<PseudoInstruction> instr = printInstructionGenerator(
				expr.accept(this), returnedOperand, expr.getType());
		instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(NEW_LINE_CHAR)));
		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(PrintStatementAST printStat) {
		ExprAST expr = printStat.getExpr();
		return printInstructionGenerator(expr.accept(this), returnedOperand,
				expr.getType());
	}

	private Deque<PseudoInstruction> printInstructionGenerator(
			Deque<PseudoInstruction> instr, Register retReg, Type t) {

		if (t.equals(BaseType.T_int)) {
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					INT_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			// Offset the size at the start
			instr.add(new Add(retReg, new ImmediateValue(4)));
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.add(new Sub(retReg, new ImmediateValue(4)));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					STRING_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType()) || t.equals(new PairType())) {
			// If it is a pair or array, print the address
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					ADDRESS_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_char)) {
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					PRINT_CHAR_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_bool)) {
			Label falsel = new Label(
					ControlFlowLabelGenerator.getNewUniqueLabel());
			Label end = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
			instr.addLast(new Cmp(retReg, new ImmediateValue(0)));
			instr.addLast(new BranchInstruction(Condition.EQ, falsel));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(TRUE_LABEL
					.getName())));
			instr.addLast(new BranchInstruction(Condition.AL, end));
			instr.addLast(falsel);
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					FALSE_LABEL.getName())));
			instr.addLast(end);
		} /*
		 * else if (t.equals(new PairType(null, null, null))) {
		 * instr.addLast(new Mov(ArmRegister.r1, retReg)); instr.addLast(new
		 * Ldr(ArmRegister.r0, new ImmediateValue("msg_0"))); instr.addLast(new
		 * Add(ArmRegister.r0, ArmRegister.r0, new ImmediateValue(4))); }
		 */

		instr.addLast(new BLInstruction("printf"));

		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReadStatementAST readStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Register returnVal = readStat.getAssignable().getScope()
				.lookup(readStat.getAssignable().getName())
				.getTemporaryRegister();

		Operand storeTo = new ImmediateValue(INT_SCANF_STORE_LABEL);

		if (readStat.getAssignable() instanceof FstAST) {
			storeTo = returnVal;

			// Check for null pointers
			instrList.add(new Mov(ArmRegister.r0, storeTo));
			instrList.add(new BLInstruction("p_check_null_pointer"));

		} else if (readStat.getAssignable() instanceof SndAST) {
			storeTo = returnVal;

			// Check for null pointers
			instrList.add(new Mov(ArmRegister.r0, storeTo));
			instrList.add(new BLInstruction("p_check_null_pointer"));

			instrList.add(new Add(storeTo, returnVal, new ImmediateValue(4)));
			
		} else if (readStat.getAssignable() instanceof ArrayElemAST) {
			ArrayElemAST arrayElem = (ArrayElemAST) readStat.getAssignable();

			int typeSize = 4;
			if (arrayElem.getType().equals(BaseType.T_bool)
					|| arrayElem.getType().equals(BaseType.T_char)) {
				typeSize = 1;
			}

			// Get the element at expr[n] then treat the element at expr[n] as
			// another array and get the element expr[n+1] in that array and so
			// on
			Register trOffset = trg.generate(weight);

			Register typeSizeReg = trg.generate(weight);
			instrList.add(new Ldr(typeSizeReg, new ImmediateValue(typeSize)));

			Register array = arrayElem.getScope().lookup(arrayElem.getName())
					.getTemporaryRegister();
			Register arrayCopy = trg.generate(weight);
			instrList.add(new Mov(arrayCopy, array));

			for (int i = 0; i < arrayElem.getExprs().size(); i++) {
				instrList.addAll(arrayElem.getExprs().get(i).accept(this));

				// r0 is the index and r1 is the size of the array. This is used
				// for
				// checking that the index is correct
				instrList.add(new Mov(ArmRegister.r0, returnedOperand));
				instrList.add(new Ldr(ArmRegister.r1, new Address(arrayCopy)));
				instrList.add(new BLInstruction("p_check_array_bounds"));

				// Multiply the index by the size of the array
				instrList.add(new Mul(trOffset, returnedOperand, typeSizeReg));
				// Add 4 since the first element of every array is the size
				// which is
				// always 4 bytes
				instrList.add(new Add(trOffset, new ImmediateValue(4)));

				// Load array element at offset into array (assuming it will be
				// another array address
				if (i == arrayElem.getExprs().size() - 1) {
					instrList.add(new Add(storeTo, arrayCopy, trOffset));

					// Check for null pointers
					instrList.add(new Mov(ArmRegister.r0, storeTo));
					instrList.add(new BLInstruction("p_check_null_pointer"));

				} else {
					instrList.add(new Ldr(arrayCopy, new Address(arrayCopy,
							trOffset)));
				}
			}
		}

		if (readStat.getAssignable().getType().equals(BaseType.T_int)) {

			if (readStat.getAssignable() instanceof VariableAST) {
				instrList.add(new Ldr(ArmRegister.r1, storeTo));
			} else {
				instrList.add(new Mov(ArmRegister.r1, storeTo));
			}

			instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(
					INT_FORMAT_LABEL)));
			instrList.add(new BLInstruction("scanf"));

		} else if (readStat.getAssignable().getType().equals(BaseType.T_char)) {

			if (readStat.getAssignable() instanceof VariableAST) {
				instrList.add(new Ldr(ArmRegister.r1, storeTo));
			} else {
				instrList.add(new Mov(ArmRegister.r1, storeTo));
			}

			instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(
					SCAN_CHAR_FORMAT_LABEL)));
			instrList.add(new BLInstruction("scanf"));

		} /*
		 * else if (readStat.getAssignable().getType() .equals(new
		 * WaccArrayType(BaseType.T_char))) {
		 * 
		 * instrList.add(new Mov(ArmRegister.r1, new ImmediateValue(100)));
		 * instrList.add(new BLInstruction("malloc")); instrList.add(new
		 * Mov(returnVal, ArmRegister.r0)); instrList.add(new
		 * Mov(ArmRegister.r1, ArmRegister.r0)); instrList.add(new
		 * Ldr(ArmRegister.r0, new ImmediateValue( STRING_FORMAT_LABEL)));
		 * instrList.add(new BLInstruction("scanf")); }
		 */

		if (readStat.getAssignable() instanceof VariableAST) {
			instrList.add(new Ldr(ArmRegister.r1, storeTo));
			instrList.add(new Ldr(returnVal, new Address(ArmRegister.r1)));
		}

		// returnedOperand = returnVal;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ExitStatementAST exitStat) {

		// /* syscall exit(int status) */
		// mov %r0, $0 /* status -> 0 */
		// mov %r7, $1 /* exit is syscall #1 */
		// swi $0 /* invoke syscall */

		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// TODO: fill in the null here for the operand visitor of the expression
		instrList.addAll(exitStat.getExpr().accept(this));
		instrList.add(new Mov(ArmRegister.r0, returnedOperand));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(FreeStatementAST freeStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(freeStat.getExpr().accept(this));
		Register pair = returnedOperand;

		instrList.add(new Mov(ArmRegister.r0, pair));

		// Check and see if the index is negative or 0
		instrList.add(new Cmp(ArmRegister.r0, new ImmediateValue(0)));
		instrList.add(new BLInstruction("p_null_reference_exception",
				Condition.EQ));

		// Hopefully this should just free the whole pair
		instrList.add(new BLInstruction("free"));

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ArrayElemAST arrayElem) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		weight = weight * 5;
		
		int typeSize = 4;
		if (arrayElem.getType().equals(BaseType.T_bool)
				|| arrayElem.getType().equals(BaseType.T_char)) {
			typeSize = 1;
		}

		// Get the element at expr[n] then treat the element at expr[n] as
		// another array and get the element expr[n+1] in that array and so on
		Register trOffset = trg.generate(weight);

		Register typeSizeReg = trg.generate(weight);
		instrList.add(new Ldr(typeSizeReg, new ImmediateValue(typeSize)));

		Register array = arrayElem.getScope().lookup(arrayElem.getName())
				.getTemporaryRegister();
		Register arrayCopy = trg.generate(weight);
		instrList.add(new Mov(arrayCopy, array));

		for (int i = 0; i < arrayElem.getExprs().size(); i++) {
			instrList.addAll(arrayElem.getExprs().get(i).accept(this));

			// r0 is the index and r1 is the size of the array. This is used for
			// checking that the index is correct
			instrList.add(new Mov(ArmRegister.r0, returnedOperand));
			instrList.add(new Ldr(ArmRegister.r1, new Address(arrayCopy)));
			instrList.add(new BLInstruction("p_check_array_bounds"));

			// Multiply the index by the size of the array
			instrList.add(new Mul(trOffset, returnedOperand, typeSizeReg));
			// Add 4 since the first element of every array is the size which is
			// always 4 bytes
			instrList.add(new Add(trOffset, new ImmediateValue(4)));

			// Load array element at offset into array (assuming it will be
			// another array address
			// Only the last array will have the size of the typeSize, the
			// others will have 4 bytes as they will be address to other arrays
			if (i == arrayElem.getExprs().size() - 1) {
				if (typeSize == 4) {
					instrList.add(new Ldr(arrayCopy, new Address(arrayCopy,
							trOffset)));
				} else {
					instrList.add(new LdrB(arrayCopy, new Address(arrayCopy,
							trOffset)));
				}
			} else {
				instrList.add(new Ldr(arrayCopy, new Address(arrayCopy,
						trOffset)));
			}
		}
		weight = weight / 5;
		
		returnedOperand = arrayCopy;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(FstAST fst) {
		// should factor out code for fst/snd into one method as its basically
		// identical accept the address
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Register fstReg = trg.generate(weight);

		Register ret = fst.getScope().lookup(fst.getName())
				.getTemporaryRegister();

		// Check for null pointers
		instrList.add(new Mov(ArmRegister.r0, ret));
		instrList.add(new BLInstruction("p_check_null_pointer"));

		instrList.add(new Ldr(fstReg, new Address(ret)));

		returnedOperand = fstReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(SndAST snd) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		final int PAIRSIZE = 8;
		int sndAddr = PAIRSIZE / 2;

		Register sndReg = trg.generate(weight);

		Register ret = snd.getScope().lookup(snd.getName())
				.getTemporaryRegister();

		// Check for null pointers
		instrList.add(new Mov(ArmRegister.r0, ret));
		instrList.add(new BLInstruction("p_check_null_pointer"));

		instrList.add(new Ldr(sndReg, new Address(ret, sndAddr)));

		returnedOperand = sndReg;

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ArrayLiterAST arrayLiter) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Register tr1 = trg.generate(weight);
		Register tr2 = trg.generate(weight);

		int typeSize = 4;
		if (arrayLiter.getType().equals(new WaccArrayType(BaseType.T_bool))
				|| arrayLiter.getType().equals(new WaccArrayType(BaseType.T_char))) {
			typeSize = 1;
		}

		// Size should be the num of elems in the array * size of the type + 4
		// You add 4 because of the size of the array that you are storing in
		// index 0
		int size = arrayLiter.getExprList().size() * typeSize + 4;
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(size)));

		instrList.add(new BLInstruction("malloc"));

		instrList.add(new Mov(tr1, ArmRegister.r0));

		// The size of the first element of every array is 4 bytes for storing
		// the length of the array
		int offset = 4;
		for (ExprAST expr : arrayLiter.getExprList()) {

			instrList.addAll(expr.accept(this));

			// Result of evaluating expr stored in returnedOperand
			// Store register returnedOperand to memory at [r4, #offset]
			if (typeSize == 4) {
				instrList
						.add(new Str(returnedOperand, new Address(tr1, offset)));
			} else {
				instrList.add(new StrB(returnedOperand,
						new Address(tr1, offset)));
			}

			offset += typeSize;
		}

		// Store size of array at offset 0
		instrList.add(new Ldr(tr2, new ImmediateValue(arrayLiter.getExprList()
				.size())));
		instrList.add(new Str(tr2, new Address(tr1)));

		returnedOperand = tr1;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(CallAST call) {
		Iterator<ExprAST> i = call.getArgList().getExprList()
				.descendingIterator();
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		while (i.hasNext()) {
			instrList.addAll(i.next().accept(this));
			instrList.add(new Push(returnedOperand));
		}
		instrList.add(new BLInstruction(call.getIdent()));
		// remove all the arguemnts from the stack
		instrList.add(new Add(ArmRegister.sp, new ImmediateValue(call
				.getArgList().getExprList().size() * 4)));

		returnedOperand = ArmRegister.r0;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(NewPairAST newPair) {
		// This new Newpair implementation is now flattened
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Register tr1 = trg.generate(weight);

		// For storing two elements
		final int PAIRSIZE = 8;

		// Allocate memory for pair
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(PAIRSIZE)));
		instrList.add(new BLInstruction("malloc"));
		instrList.add(new Mov(tr1, ArmRegister.r0));

		// First element
		instrList.addAll(newPair.getExprL().accept(this));
		// Move the result of evaluating expr into tr2
		instrList.add(new Str(returnedOperand, new Address(tr1)));

		// Second element
		instrList.addAll(newPair.getExprR().accept(this));
		// Move the result of evaluating expr into tr2
		instrList.add(new Str(returnedOperand, new Address(tr1, PAIRSIZE / 2)));

		// Should store memory address of pair
		returnedOperand = tr1;
		return instrList;
	}

	private Deque<PseudoInstruction> checkIntegerOverFlow() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList
				.add(new BLInstruction("p_throw_overflow_error", Condition.VS));
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(BinaryExprAST binExpr) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(binExpr.getExprL().accept(this));
		Register exprRegL = returnedOperand;

		instrList.addAll(binExpr.getExprR().accept(this));
		Register exprRegR = returnedOperand;

		Register destReg = trg.generate(weight);

		// TODO: Add instructions for each binary op
		switch (binExpr.getBinaryOp()) {
		case MUL:
			instrList.add(new Smull(destReg, exprRegL, exprRegL, exprRegR));
			instrList.add(new Cmp(exprRegL, new Operand2(destReg, "ASR #31")));
			
			instrList.add(new BLInstruction("p_throw_overflow_error", Condition.NE));
			break;
		case DIV:
			instrList.add(new Mov(ArmRegister.r0, exprRegL));
			instrList.add(new Mov(ArmRegister.r1, exprRegR));
			instrList.add(new BLInstruction("p_check_divide_by_zero"));

			instrList.add(new BLInstruction("__aeabi_idiv"));

			instrList.add(new Mov(destReg, ArmRegister.r0));
			break;
		case MOD:
			instrList.add(new Mov(ArmRegister.r0, exprRegL));
			instrList.add(new Mov(ArmRegister.r1, exprRegR));
			instrList.add(new BLInstruction("p_check_divide_by_zero"));

			instrList.add(new BLInstruction("__aeabi_idivmod"));

			instrList.add(new Mov(destReg, ArmRegister.r1));
			break;
		case PLUS:
			// add destReg trL trR
			instrList.add(new Add(destReg, exprRegL, exprRegR));
			instrList.addAll(checkIntegerOverFlow());
			break;
		case MINUS:
			// sub destReg trL trR
			instrList.add(new Sub(destReg, exprRegL, exprRegR));
			instrList.addAll(checkIntegerOverFlow());
			break;
		case GT:
			// cmp trL trR
			// movgt destReg, #1
			// movle destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));

			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.GT));

			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.LE));
			break;
		case GTE:
			// cmp trL trR
			// movge destReg, #1
			// movlt destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.GE));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.LT));
			break;
		case LT:
			// cmp trL trR
			// movlt destReg, #1
			// movge destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.LT));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.GE));
			break;
		case LTE:
			// cmp trL trR
			// movle destReg, #1
			// movgt destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.LE));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.GT));
			break;
		case EQ:
			// cmp trL trR
			// moveq destReg, #1
			// movne destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.EQ));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.NE));
			break;
		case NEQ:
			// cmp trL trR
			// movne destReg, #1
			// moveq destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.NE));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.EQ));
			break;
		case AND:
			// and destReg trL trR
			instrList.add(new And(destReg, exprRegL, exprRegR));
			break;
		case OR:
			// and destReg trL trR
			instrList.add(new Orr(destReg, exprRegL, exprRegR));
			break;
		}

		returnedOperand = destReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(UnaryExprAST unExpr) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(unExpr.getExpr().accept(this));

		Register tr1 = returnedOperand;
		Register tr2 = trg.generate(weight);

		// TODO: Add instructions for each unary op
		switch (unExpr.getUnaryOp()) {
		case NOT:
			// and tr tr 0
			instrList.add(new Eor(tr1, tr1, new ImmediateValue(1)));
			break;
		case MINUS:
			// sub tr 0 tr
			instrList.add(new Mov(tr2, new ImmediateValue(0)));
			instrList.add(new Sub(tr1, tr2, tr1));
			instrList.addAll(checkIntegerOverFlow());
			break;
		case LEN:
			// ldr tr, [tr] (the element at index 0 is the length)
			instrList.add(new Ldr(tr1, new Address(tr1, 0)));
			break;
		case ORD:
			break;
		case CHR:
			break;
		}

		returnedOperand = tr1;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ValueExprAST valueExpr) {
		Label literalLabel = new Label(
				LiteralLabelGenerator.getNewUniqueLabel());
		Register ret = trg.generate(weight);
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// Literal is added to the .data section
		CompileProgramAST.getDataSection().add(literalLabel);
		if (valueExpr.getLiter() instanceof ArrayElemAST) {
			instrList
					.addAll(((ArrayElemAST) valueExpr.getLiter()).accept(this));
			ret = returnedOperand;
		} else if (valueExpr.getLiter() instanceof PairLiter) {
			instrList.add(new Ldr(ret, new ImmediateValue(0)));
		} else if (valueExpr.getType().equals(
				new WaccArrayType(BaseType.T_char))) {
			CompileProgramAST.getDataSection().add(
					new AssemblerDirective(".word "
							+ valueExpr.getValue().length()));
			CompileProgramAST.getDataSection().add(
					new AssemblerDirective(".ascii \"" + valueExpr.getValue()
							+ "\\0\""));
			instrList.add(new Ldr(ret, new ImmediateValue(literalLabel
					.getName())));
		} else if (valueExpr.getType().equals(BaseType.T_int)) {
			int intVal = Integer.parseInt(valueExpr.getValue());
			instrList.add(new Ldr(ret, new ImmediateValue(intVal)));
		} else if (valueExpr.getType().equals(BaseType.T_char)) {
			instrList.add(new Mov(ret, new ImmediateValue(valueExpr.getValue(),
					true)));
		} else if (valueExpr.getType().equals(BaseType.T_bool)) {
			if (valueExpr.getValue().equals("true")) {
				instrList.add(new Mov(ret, new ImmediateValue(1)));
			} else {
				instrList.add(new Mov(ret, new ImmediateValue(0)));
			}
		}

		returnedOperand = ret;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(VariableAST variable) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		returnedOperand = variable.getScope().lookup(variable.getName())
				.getTemporaryRegister();
		return instrList;
	}

	public TemporaryRegisterGenerator getTemporaryRegisterGenerator() {
		return trg;
	}

	@Override
	public Deque<PseudoInstruction> visit(IfWithoutElseStatement ifStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		Label endl = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());

		instrList.addAll(ifStat.getCond().accept(this));
		instrList.add(new Cmp(returnedOperand, new ImmediateValue(0)));
		instrList.add(new BranchInstruction(Condition.EQ, endl));

		weight = weight / 2;
		instrList.addAll(ifStat.getTrueStats().accept(this));
		instrList.add(endl);

		weight = (weight + 1) * 2;
		return instrList;
	}
}
