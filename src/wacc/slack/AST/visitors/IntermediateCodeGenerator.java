package wacc.slack.AST.visitors;

import java.util.Deque;
import java.util.LinkedList;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArrayElemAST;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.assignables.NewPairAST;
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
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.Operand;
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
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Mul;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Str;
import wacc.slack.instructions.Sub;

// NB: use LinkedList 

public class IntermediateCodeGenerator implements
		ASTVisitor<Deque<PseudoInstruction>> {

	private static final String STRING_FORMAT_LABEL = "string_format";
	private static final String CHAR_FORMAT_LABEL = "char_format";
	private static final String NEW_LINE_CHAR = "new_line_char";
	private static final String INT_FORMAT_LABEL = "int_format";
	private static final String INT_SCANF_STORE_LABEL = "intScanfStoreLabel";
	private static final Label TRUE_LABEL = new Label("l_true");
	private static final Label FALSE_LABEL = new Label("l_false");

	// To print error messages, get a printstatementast.accept and add it to
	// instrListSpilledRegistersHaveTheRestOfTheRegistersInThem
	private static final PrintStatementAST NEGATIVE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: negative index.\"", null),
					null), null);
	
	private static final PrintStatementAST DIVIDE_BY_ZERO_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"DivideByZeroError: expression has divisor of zero .\"", null), null),
			null);

	private static final PrintStatementAST LARGE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: index too large.\"", null),
					null), null);

	private static final PrintStatementAST NULL_REFERENCE_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"NullReferenceError: dereference a null reference.\"",
					null), null), null);

	private final class DefaultOperandVisitor implements
			OperandVisitor<Operand> {

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

	}

	private Deque<PseudoInstruction> textSection = new LinkedList<>();
	private Deque<PseudoInstruction> dataSection = new LinkedList<>();
	private Deque<PseudoInstruction> compilerDefinedFunctions = new LinkedList<>();

	private Register returnedOperand = null;

	/*
	 * contains the current weight of registers for current scope, use everytime
	 * you create a register
	 */
	private int weight = 0;
	private TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();

	@Override
	public Deque<PseudoInstruction> visit(ProgramAST prog) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// d = doCFG(d) // replaces all the temporarz registers with real ones
		// and do the optimization

		instrList.add(new AssemblerDirective(".global main"));
		// TODO: functions, exit codes maybe

		// --- implementation of the "main" function, i.e. the stats after the
		// function definitions

		Operand stackSpace = new ImmediateValue(trg);

		instrList.add(new Label("main"));
		instrList.add(new Push(ArmRegister.lr));
		instrList.add(new Sub(ArmRegister.sp, stackSpace));
		instrList.addAll(prog.getStatements().accept(this));
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue("0")));
		instrList.add(new Add(ArmRegister.sp, stackSpace));
		instrList.add(new Pop(ArmRegister.pc));

		initCompilerDefinedFunctions();
		instrList.addAll(compilerDefinedFunctions);

		instrList.add(new AssemblerDirective(".data"));
		initDataSection();
		instrList.addAll(dataSection);

		instrList.add(new AssemblerDirective(".text"));
		initTextSection();
		instrList.addAll(textSection);

		return instrList;
	}

	private void initDataSection() {
		dataSection.add(new Label(INT_SCANF_STORE_LABEL));
		dataSection.add(new AssemblerDirective(".word 0"));
	}

	private void initTextSection() {
		textSection.add(new Label(NEW_LINE_CHAR));
		textSection.add(new AssemblerDirective("\t.ascii \"\\n\\0\""));

		textSection.add(new Label(STRING_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%s\\0\""));

		textSection.add(new Label(CHAR_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%c\\0\""));

		textSection.add(new Label(INT_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%d\\0\""));

		textSection.add(new Label(TRUE_LABEL.getName()));
		textSection.add(new AssemblerDirective("\t.ascii \"true\\0\""));

		textSection.add(new Label(FALSE_LABEL.getName()));
		textSection.add(new AssemblerDirective("\t.ascii \"false\\0\""));
	}

	// COMPILER ADDED FUNCTIONS
	// ie. p_check_array_bounds etc

	private void initCompilerDefinedFunctions() {

		// TODO: Make sure checkArrayBoundsAsm() is only added when an array
		// elem is seen in the code
		if (true) {
			compilerDefinedFunctions.addAll(checkArrayBoundsAsm());
			compilerDefinedFunctions.addAll(checkNullPointerAsm());
			compilerDefinedFunctions.addAll(nullReferenceErrorAsm());
			compilerDefinedFunctions.addAll(checkDivideByZero());
			compilerDefinedFunctions.addAll(divideByZeroError());

		}
	}
	
	private Deque<PseudoInstruction> checkDivideByZero() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		
		instrList.add(new Label("p_check_divide_by_zero"));
		instrList.add(new Push(ArmRegister.lr));
		instrList.add(new Cmp(ArmRegister.r1, new ImmediateValue(0)));
		
		instrList.add(new BLInstruction("p_divide_by_zero_exception", Condition.EQ));
		
		instrList.add(new Pop(ArmRegister.pc));
		
		return instrList;
	}
	
	private Deque<PseudoInstruction> divideByZeroError() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		
		instrList.add(new Label("p_divide_by_zero_exception"));
		instrList.add(new Push(ArmRegister.lr));
		
		instrList.addAll(DIVIDE_BY_ZERO_ERROR.accept(this));

		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));
		
		return instrList;
	}
	
	private Deque<PseudoInstruction> freePairAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_free_pair"));
		instrList.add(new Push(ArmRegister.lr));
		
		// Check and see if the index is negative or 0 
		instrList.add(new Cmp(ArmRegister.r0, new ImmediateValue(0)));
		instrList.add(new BLInstruction("p_null_reference_exception",
				Condition.EQ));
		
		// Hopefully this should just free the whole pair
		instrList.add(new BLInstruction("free"));
		
		instrList.add(new Pop(ArmRegister.pc));
		
		return instrList;
	}
	
	// Needs to be added explicitly when fst/snd are used
	private Deque<PseudoInstruction> checkNullPointerAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_check_null_pointer"));
		instrList.add(new Push(ArmRegister.lr));

		// Check and see if the index is negative or 0
		instrList.add(new Cmp(ArmRegister.r0, new ImmediateValue(0)));
		instrList.add(new BLInstruction("p_null_reference_exception",
				Condition.LE));

		instrList.add(new Pop(ArmRegister.pc));

		return instrList;
	}

	private Deque<PseudoInstruction> nullReferenceErrorAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_null_reference_exception"));
		instrList.addAll(NULL_REFERENCE_ERROR.accept(this));

		// Exit the program
		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	// Needs to be added explicitly when an array elem is used
	private Deque<PseudoInstruction> checkArrayBoundsAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_check_array_bounds"));
		instrList.add(new Push(ArmRegister.lr));

		// Check and see if the index is negative
		instrList.add(new Cmp(ArmRegister.r0, new ImmediateValue(0)));
		instrList.add(new BLInstruction("p_negative_index_exception",
				Condition.LT));

		// Check and see if the index is too large
		instrList.add(new Ldr(ArmRegister.r1, new Address(ArmRegister.r1)));
		instrList.add(new Cmp(ArmRegister.r0, ArmRegister.r1));
		instrList
				.add(new BLInstruction("p_large_index_exception", Condition.CS));

		instrList.add(new Pop(ArmRegister.pc));

		// If checkArrayBounds is called, it will need negative and
		// largeIndexExceptionAsm
		instrList.addAll(negativeIndexExceptionAsm());
		instrList.addAll(largeIndexExceptionAsm());

		return instrList;
	}

	// Do not add explicitly
	private Deque<PseudoInstruction> negativeIndexExceptionAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_negative_index_exception"));
		instrList.addAll(NEGATIVE_INDEX_ERROR.accept(this));

		// Exit the program
		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	// Do not add explicitly
	private Deque<PseudoInstruction> largeIndexExceptionAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_large_index_exception"));
		instrList.addAll(LARGE_INDEX_ERROR.accept(this));

		// Exit the program
		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	// TODO: Implement this
	private Deque<PseudoInstruction> checkDivideByZeroAsm() {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_check_divide_by_zero"));
		instrList.add(new Push(ArmRegister.lr));

		instrList.add(new Pop(ArmRegister.pc));
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(FuncAST func) {
		// TODO Auto-generated method stub
		return null;
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
		
		Register destReg = trg.generate(weight);
		Register tr = trg.generate(weight);
		instrList.addAll(assignStat.getRhs().accept(this));
		instrList.add(new Mov(destReg, returnedOperand));
		
		// Set the variable identinfo to store this temp reg
		if (!(destReg instanceof TemporaryRegister)) {
			throw new RuntimeException(
					"Variable assignRHS should never be put in a real register.");
		}

		if (assignStat.getLhs() instanceof ArrayElemAST) {
			// Assigning array elems
			// Figure out the type size
			Register typeSizeReg = trg.generate(weight);
			int typeSize = 4;
			if (((ArrayElemAST) assignStat.getLhs()).getType().equals(
					BaseType.T_bool)
					|| ((ArrayElemAST) assignStat.getLhs()).getType().equals(
							BaseType.T_char)) {
				typeSize = 1;
			}
			instrList.add(new Mov(typeSizeReg, new ImmediateValue(typeSize)));

			// Array address is stored in this register
			Register arrayReg = assignStat.getLhs().getScope()
					.lookup(assignStat.getLhs().getName())
					.getTemporaryRegister();

			// TODO: Nested assignments and check array bounds
			// Index is stored in this register
			Register index = trg.generate(weight);
			instrList.addAll(((ArrayElemAST) assignStat.getLhs()).getExprs()
					.get(0).accept(this));
			// Increment by one
			instrList.add(new Mov(index, returnedOperand));
			instrList.add(new Add(index, index, new ImmediateValue(1)));
			
			// Multiply by type size
			instrList.add(new Mul(index, index, typeSizeReg));

			// Store the rhs in the array at offset index
			instrList.add(new Str(destReg, new Address(arrayReg, index)));
		} else if (assignStat.getLhs() instanceof FstAST) {
			FstAST fst = (FstAST) assignStat.getLhs();
			Register ret = fst.getScope().lookup(fst.getName())
					.getTemporaryRegister();
			instrList.add(new Str(destReg, new Address(ret)));
		} else if (assignStat.getLhs() instanceof SndAST) {
			SndAST snd = (SndAST) assignStat.getLhs();
			Register ret = snd.getScope().lookup(snd.getName())
					.getTemporaryRegister();
			instrList.add(new Str(destReg, new Address(ret, 4)));
		} else {
			// Assigning variables
			assignStat.getLhs().getScope()
					.lookup(assignStat.getLhs().getName())
					.setTemporaryRegister((TemporaryRegister) destReg);
		}

		returnedOperand = destReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(BeginEndAST beginEnd) {
		return new LinkedList<PseudoInstruction>();
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

		Label start = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		Label end = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		instrList.add(new BranchInstruction(Condition.AL, end));
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
		// TODO Auto-generated method stub

		return null;
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
		//System.out.println(returnedOperand);
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
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					STRING_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_char)) {
			instr.addLast(new Mov(ArmRegister.r1, retReg));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					CHAR_FORMAT_LABEL)));
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
		}

		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReadStatementAST readStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// Chatley forgive me, for I have sinned
		Register returnVal = readStat.getAssignable().getScope()
				.lookup(readStat.getAssignable().getName())
				.getTemporaryRegister();

		// read int
		if (readStat.getAssignable().getType().equals(BaseType.T_int)) {
			instrList.add(new Ldr(ArmRegister.r1, new ImmediateValue(
					INT_SCANF_STORE_LABEL)));
			instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(
					INT_FORMAT_LABEL)));
			instrList.add(new BLInstruction("scanf"));
			instrList.add(new Ldr(ArmRegister.r1, new ImmediateValue(
					INT_SCANF_STORE_LABEL)));
			instrList.add(new Ldr(returnVal, new Address(ArmRegister.r1, 0)));
			// read char
		} else if (readStat.getAssignable().getType().equals(BaseType.T_char)) {
			instrList.add(new Ldr(ArmRegister.r1, new ImmediateValue(
					INT_SCANF_STORE_LABEL)));
			instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(
					CHAR_FORMAT_LABEL)));
			instrList.add(new BLInstruction("scanf"));
			instrList.add(new Ldr(ArmRegister.r1, new ImmediateValue(
					INT_SCANF_STORE_LABEL)));
			instrList.add(new Ldr(returnVal, new Address(ArmRegister.r1, 0)));
			// read string
		} else if (readStat.getAssignable().getType()
				.equals(new WaccArrayType(BaseType.T_char))) {
			instrList.add(new Mov(ArmRegister.r1, new ImmediateValue(100)));
			instrList.add(new BLInstruction("malloc"));
			instrList.add(new Mov(returnVal, ArmRegister.r0));
			instrList.add(new Mov(ArmRegister.r1, ArmRegister.r0));
			instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(
					STRING_FORMAT_LABEL)));
			instrList.add(new BLInstruction("scanf"));
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
		
		int typeSize = 4;
		if (arrayElem.getType().equals(BaseType.T_bool)
				|| arrayElem.getType().equals(BaseType.T_char)) {
			typeSize = 1;
		}

		// Get the element at expr[n] then treat the element at expr[n] as
		// another array and get the element expr[n+1] in that array and so on

		Register trOffset = trg.generate(weight);
		Register tr = trg.generate(weight);
		instrList.add(new Mov(tr, new ImmediateValue(typeSize)));

		// ldr tr1, [sp]
		// ldr tr1, [tr1 + typeSize * index]
		// mov destReg, tr1

		// register where array is stored
		Register array = arrayElem.getScope().lookup(arrayElem.getName())
				.getTemporaryRegister();

		for (int i = 0; i < arrayElem.getExprs().size(); i++) {
			instrList.addAll(arrayElem.getExprs().get(i).accept(this));

			// Move the index to trOffset
			instrList.add(new Mov(trOffset, returnedOperand));

			// Mul the index with the typeSize to get the offset
			instrList.add(new Mul(trOffset, trOffset, tr));

			// Load array element at offset into array (assuming it will be
			// another array address
			instrList.add(new Ldr(array, new Address(array, trOffset)));
		}
		
		returnedOperand = array;
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

		// This may only be the case if you want to assign fst (expr) to
		// something?
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

		// This may only be the case iFf you want to assign snd (expr) to
		// something?
		instrList.add(new Ldr(sndReg, new Address(ret, sndAddr)));

		returnedOperand = sndReg;
		
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ArrayLiterAST arrayLiter) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// Corresponding r4 and r5 to the reference compiler. May or may not be
		// actual r4 or r5
		Register tr1 = trg.generate(weight);
		Register tr2 = trg.generate(weight);

		// Needs to evaluate and add code for each expression in the array then
		// find out which register the result is in and put it in the array...
		// no lazy evaluation! this ain't no functional language

		int typeSize = 4;
		if (arrayLiter.getType().equals(BaseType.T_bool)
				|| arrayLiter.getType().equals(BaseType.T_char)) {
			typeSize = 1;
		}

		// Size should be the num of elems in the array + 1 * size of the type
		// You add one to the num of elems because you need to store the size of
		// the array as well
		int size = (arrayLiter.getExprList().size() + 1) * typeSize;
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(size)));

		instrList.add(new BLInstruction("malloc"));

		instrList.add(new Mov(tr1, ArmRegister.r0));

		int offset = typeSize;
		for (ExprAST expr : arrayLiter.getExprList()) {

			instrList.addAll(expr.accept(this));

			// Result of evaluating expr stored in returnedOperand
			// Store register returnedOperand to memory at [r4, #offset]
			instrList.add(new Str(returnedOperand, new Address(tr1, offset)));

			offset += typeSize;
		}

		// Store size of array at offset 0
		instrList.add(new Mov(tr2, new ImmediateValue(arrayLiter.getExprList()
				.size())));
		// Store contents of register r5 into [r4]
		instrList.add(new Str(tr2, new Address(tr1, 0)));

		// Store the actual register that is at tr1 at [sp]
		// instrList.add(new Str(tr1, new Address(ArmRegister.sp, 0)));

		// returnedOperand is the temp reg which stores the memory address of
		// the array
		returnedOperand = tr1;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(CallAST call) {
		// TODO Auto-generated method stub
		return null;
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
		instrList.add(new Str(returnedOperand, new Address(tr1, PAIRSIZE/2)));

		// Should store memory address of pair
		returnedOperand = tr1;
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
			// add destReg trL trR
			instrList.add(new Mul(destReg, exprRegL, exprRegR));
			break;
		case DIV:
			instrList.add(new Mov(ArmRegister.r0, exprRegL));
			instrList.add(new Mov(ArmRegister.r1, exprRegR));
			instrList.add(new BLInstruction("p_check_divide_by_zero"));
			
			instrList.add(new BLInstruction("__aeabi_idiv"));
			
			instrList.add(new Mov(destReg, ArmRegister.r0));
			break;
		case MOD:
			break;
		case PLUS:
			// add destReg trL trR
			instrList.add(new Add(destReg, exprRegL, exprRegR));
			break;
		case MINUS:
			// sub destReg trL trR
			instrList.add(new Sub(destReg, exprRegL, exprRegR));
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
		textSection.add(literalLabel);
		
		if (valueExpr.getLiter() instanceof ArrayElemAST) {
			instrList
					.addAll(((ArrayElemAST) valueExpr.getLiter()).accept(this));
			ret = returnedOperand;
		} else if (valueExpr.getLiter() instanceof PairLiter) {
			instrList.add(new Ldr(ret, new ImmediateValue(0)));
		} else if (valueExpr.getType().equals(
				new WaccArrayType(BaseType.T_char))) {
			textSection.add(new AssemblerDirective(".ascii \""
					+ valueExpr.getValue() + "\\0\""));
			instrList.add(new Ldr(ret, new ImmediateValue(literalLabel
					.getName())));
		} else if (valueExpr.getType().equals(BaseType.T_int)) {
			int intVal = Integer.parseInt(valueExpr.getValue());
			instrList.add(new Ldr(ret, new ImmediateValue(intVal)));
		} else if (valueExpr.getType().equals(BaseType.T_char)) {
			/*
			 * textSection.add(new AssemblerDirective(".byte '" +
			 * valueExpr.getValue() + "'")); instrList.add(new Ldr(ret, new
			 * ImmediateValue(literalLabel .getName())));
			 */
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
}
