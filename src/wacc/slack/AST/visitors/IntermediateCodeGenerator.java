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

// NB: use LinkedList 

public class IntermediateCodeGenerator implements
		ASTVisitor<Deque<PseudoInstruction>> {

	private static final String STRING_FORMAT_LABEL = "string_format";
	private static final String CHAR_FORMAT_LABEL = "char_format";
	private static final String NEW_LINE_CHAR = "new_line_char";
	private static final String INT_FORMAT_LABEL = "int_format";
	private static final Label TRUE_LABEL = new Label("l_true");
	private static final Label FALSE_LABEL = new Label("l_false");

	// To print error messages, get a printstatementast.accept and add it to
	// instrList
	private static final PrintStatementAST NEGATIVE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: negative index.\"", null), null),
			null);

	private static final PrintStatementAST LARGE_INDEX_ERROR = new PrintStatementAST(
			new ValueExprAST(new StringLiter(
					"\"ArrayOutOfBoundsException: index too large.\"", null), null),
			null);

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

	}

	private Deque<PseudoInstruction> textSection = new LinkedList<>();
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

		instrList.add(new Label("main"));
		instrList.add(new Push(ArmRegister.lr));
		instrList.addAll(prog.getStatements().accept(this));
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue("0")));
		instrList.add(new Pop(ArmRegister.pc));

		initCompilerDefinedFunctions();
		instrList.addAll(compilerDefinedFunctions);

		instrList.add(new AssemblerDirective(".text"));
		initTextSection();
		instrList.addAll(textSection);

		return instrList;
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

		// TODO: Make sure checkArrayBoundsAsm() is only added when an array elem is seen in the code
		if (true) {
			compilerDefinedFunctions.addAll(checkArrayBoundsAsm());
		}
		
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

		instrList.addAll(assignStat.getRhs().accept(this));
		Register destReg = trg.generate(weight);

		// This might be unnecessary
		instrList.add(new Mov(destReg, returnedOperand));

		// Set the variable identinfo to store this temp reg
		if (!(destReg instanceof TemporaryRegister)) {
			throw new RuntimeException(
					"Variable assignRHS should never be put in a real register.");
		}

		assignStat.getLhs().getScope().lookup(assignStat.getLhs().getName())
				.setTemporaryRegister((TemporaryRegister) destReg);

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
		return printInstructionGenerator(expr.accept(this), returnedOperand,
				expr.getType());
	}

	private Deque<PseudoInstruction> printInstructionGenerator(
			Deque<PseudoInstruction> instr, Register retReg, Type t) {

		instr.addLast(new Mov(ArmRegister.r1, retReg));
		if (t.equals(BaseType.T_int)) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					INT_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					STRING_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					CHAR_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_bool)) {
			// TODO:
			// instr.addLast(new Mov(ArmRegister.r0, retReg));
		}

		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReadStatementAST readStat) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		Register destReg = trg.generate(weight);

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
			instrList.add(new Mul(trOffset, trOffset, new ImmediateValue(
					typeSize)));

			// Load array element at offset into array (assuming it will be
			// another array address
			instrList.add(new Ldr(array, new Address(array, trOffset)));
		}

		// Array element is not another array element
		instrList.add(new Mov(destReg, array));

		returnedOperand = destReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(FstAST fst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(SndAST snd) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: Replace arm registers with temp ones where possible. Check
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
		instrList.add(new Ldr(tr2, new ImmediateValue(arrayLiter.getExprList()
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
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Register tr1 = trg.generate(weight);
		Register tr2 = trg.generate(weight);

		// For storing 2 address each 4 bytes
		final int PAIRSIZE = 8;

		// Get element sizes
		int typeSizeFst = 4;
		int typeSizeSnd = 4;

		if (newPair.getExprL().getType().equals(BaseType.T_bool)
				&& newPair.getExprL().getType().equals(BaseType.T_char)) {
			typeSizeFst = 1;
		}

		if (newPair.getExprR().getType().equals(BaseType.T_bool)
				&& newPair.getExprR().getType().equals(BaseType.T_char)) {
			typeSizeSnd = 1;
		}

		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(PAIRSIZE)));
		instrList.add(new BLInstruction("malloc"));
		instrList.add(new Mov(tr1, ArmRegister.r0));

		// First element
		instrList.addAll(newPair.getExprL().accept(this));
		// Move the result of evaluating expr into tr2
		instrList.add(new Mov(tr2, returnedOperand));
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(typeSizeFst)));
		instrList.add(new BLInstruction("malloc"));
		// This may need to be STRB for chars
		instrList.add(new Str(tr2, new Address(ArmRegister.r0, 0)));
		instrList.add(new Str(ArmRegister.r0, new Address(tr1, 0)));

		// Second element
		instrList.addAll(newPair.getExprR().accept(this));
		// Move the result of evaluating expr into tr2
		instrList.add(new Mov(tr2, returnedOperand));
		instrList.add(new Ldr(ArmRegister.r0, new ImmediateValue(typeSizeSnd)));
		instrList.add(new BLInstruction("malloc"));
		// This may need to be STRB for chars
		instrList.add(new Str(tr2, new Address(ArmRegister.r0, 0)));
		instrList.add(new Str(ArmRegister.r0, new Address(tr1, PAIRSIZE)));

		// Store register at tr1 in [sp]
		// Don't need to do this as we are not storing on the stack, just
		// keeping in register. Keep just in case we need later
		// instrList.add(new Str(tr1, new Address(ArmRegister.sp, 0)));

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
			instrList.add(new Cmp(exprRegR, exprRegL));
			instrList
					.add(new Mov(destReg, new ImmediateValue(1), Condition.LT));
			instrList
					.add(new Mov(destReg, new ImmediateValue(0), Condition.GE));
			break;
		case LTE:
			// cmp trL trR
			// movle destReg, #1
			// movgt destReg, #0
			instrList.add(new Cmp(exprRegR, exprRegL));
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

		Register tr = returnedOperand;

		// TODO: Add instructions for each unary op
		switch (unExpr.getUnaryOp()) {
		case NOT:
			// and tr tr 0
			instrList.add(new And(tr, tr, new ImmediateValue(0)));
			break;
		case MINUS:
			// sub tr 0 tr
			instrList.add(new Sub(tr, new ImmediateValue(0), tr));
			break;
		case LEN:
			// ldr tr, [tr] (the element at index 0 is the length)
			instrList.add(new Ldr(tr, new Address(tr, 0)));
			break;
		case ORD:
			break;
		case CHR:
			break;
		}

		returnedOperand = tr;
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
		} else if (valueExpr.getLiter() instanceof PairLiter) {
			instrList.add(new Ldr(ret, new ImmediateValue(0)));
		} else if (valueExpr.getType().equals(
				new WaccArrayType(BaseType.T_char))) {
			textSection.add(new AssemblerDirective(".ascii \""
					+ valueExpr.getValue() + "\\0\""));
			instrList.add(new Ldr(ret, new ImmediateValue(literalLabel
					.getName())));
		} else if (valueExpr.getType().equals(BaseType.T_int)) {
			instrList.add(new Mov(ret, new ImmediateValue(Integer
					.parseInt(valueExpr.getValue()))));
		} else if (valueExpr.getType().equals(BaseType.T_char)) {
			textSection.add(new AssemblerDirective(".byte '"
					+ valueExpr.getValue() + "'"));
			instrList.add(new Ldr(ret, new ImmediateValue(literalLabel
					.getName())));
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
