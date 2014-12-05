package wacc.slack.interferenceGraph;

import java.util.Deque;
import java.util.LinkedList;

import wacc.slack.GenerateOptimizedIntermediateCode;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.Operand;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.visitors.GenerateAssembly;
import wacc.slack.instructions.visitors.GenerateAssemblyBuilder;

public class CompileProgramAST {

	private final ProgramAST program;

	private static Deque<PseudoInstruction> textSection = new LinkedList<>();
	private static Deque<PseudoInstruction> dataSection = new LinkedList<>();
	private Deque<PseudoInstruction> compilerDefinedFunctions = new LinkedList<>();

	public CompileProgramAST(ProgramAST program) {
		this.program = program;

	}

	public String compile() {
		int optimisationLevel = 0;
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		try {
			instrList.add(new AssemblerDirective(".global main"));

			GenerateOptimizedIntermediateCode generateOptimizedIntermediateCode = new GenerateOptimizedIntermediateCode(
					program.getStatements(), optimisationLevel);
			Deque<PseudoInstruction> body = generateOptimizedIntermediateCode
					.call();

			// TODO: allocating stackspace is wrong, it should be without *2,
			// but that causes segfault on exiting the program
			Operand stackSpace = new ImmediateValue(
					generateOptimizedIntermediateCode.getNumberOfRegsUsed() * 2);

			instrList.add(new Label("main"));
			instrList.add(new Push(ArmRegister.lr));
			instrList.add(new Sub(ArmRegister.sp, stackSpace));

			instrList.addAll(body);

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

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return printCode(optimisationLevel, instrList);
	}

	private String printCode(int optimisationLevel,
			Deque<PseudoInstruction> optimizedCode) {
		GenerateAssembly psuedoInstructionVisitor = new GenerateAssemblyBuilder()
		// .ignoringTemporaries()
				.withOptimisationLevel(optimisationLevel).make();

		String output = "";

		// insert pseudoinstruction code change from temporary register
		// allocation here

		// realCode =

		//
		for (PseudoInstruction i : optimizedCode) {

			output += i.accept(psuedoInstructionVisitor);
		}

		return output;
	}

	private void initDataSection() {
		dataSection.add(new Label(
				IntermediateCodeGenerator.INT_SCANF_STORE_LABEL));
		dataSection.add(new AssemblerDirective(".word 0"));
	}

	private void initTextSection() {
		textSection.add(new Label(IntermediateCodeGenerator.NEW_LINE_CHAR));
		textSection.add(new AssemblerDirective("\t.ascii \"\\n\\0\""));

		textSection
				.add(new Label(IntermediateCodeGenerator.STRING_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%s\\0\""));

		textSection.add(new Label(IntermediateCodeGenerator.CHAR_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%c\\0\""));

		textSection.add(new Label(IntermediateCodeGenerator.INT_FORMAT_LABEL));
		textSection.add(new AssemblerDirective("\t.ascii \"%d\\0\""));

		textSection.add(new Label(IntermediateCodeGenerator.TRUE_LABEL
				.getName()));
		textSection.add(new AssemblerDirective("\t.ascii \"true\\0\""));

		textSection.add(new Label(IntermediateCodeGenerator.FALSE_LABEL
				.getName()));
		textSection.add(new AssemblerDirective("\t.ascii \"false\\0\""));
	}

	// COMPILER ADDED FUNCTIONS
	// ie. p_check_array_bounds etc

	private void initCompilerDefinedFunctions() throws Exception {

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

		instrList.add(new BLInstruction("p_divide_by_zero_exception",
				Condition.EQ));

		instrList.add(new Pop(ArmRegister.pc));

		return instrList;
	}

	private Deque<PseudoInstruction> divideByZeroError() throws Exception {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.add(new Label("p_divide_by_zero_exception"));
		instrList.add(new Push(ArmRegister.lr));

		// instrList.addAll(IntermediateCodeGenerator.DIVIDE_BY_ZERO_ERROR.accept(new
		// IntermediateCodeGenerator()));
		instrList.addAll(new GenerateOptimizedIntermediateCode(
				IntermediateCodeGenerator.DIVIDE_BY_ZERO_ERROR, 0).call());

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

	private Deque<PseudoInstruction> nullReferenceErrorAsm() throws Exception {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_null_reference_exception"));
		// instrList.addAll(IntermediateCodeGenerator.NULL_REFERENCE_ERROR.accept(new
		// IntermediateCodeGenerator()));
		instrList.addAll(new GenerateOptimizedIntermediateCode(
				IntermediateCodeGenerator.NULL_REFERENCE_ERROR, 0).call());

		// Exit the program
		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	// Needs to be added explicitly when an array elem is used
	private Deque<PseudoInstruction> checkArrayBoundsAsm() throws Exception {
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
	private Deque<PseudoInstruction> negativeIndexExceptionAsm()
			throws Exception {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_negative_index_exception"));
		// instrList.addAll(IntermediateCodeGenerator.NEGATIVE_INDEX_ERROR.accept(new
		// IntermediateCodeGenerator()));
		instrList.addAll(new GenerateOptimizedIntermediateCode(
				IntermediateCodeGenerator.NEGATIVE_INDEX_ERROR, 0).call());

		// Exit the program
		instrList.add(new Mov(ArmRegister.r0, new ImmediateValue(-1)));
		instrList.add(new BLInstruction("exit"));

		return instrList;
	}

	// Do not add explicitly
	private Deque<PseudoInstruction> largeIndexExceptionAsm() throws Exception {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		instrList.add(new Label("p_large_index_exception"));
		// instrList.addAll(IntermediateCodeGenerator.LARGE_INDEX_ERROR.accept(new
		// IntermediateCodeGenerator()));
		instrList.addAll(new GenerateOptimizedIntermediateCode(
				IntermediateCodeGenerator.LARGE_INDEX_ERROR, 0).call());
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

	public static Deque<PseudoInstruction> getTextSection() {
		return textSection;
	}
	
	public static Deque<PseudoInstruction> getDataSection() {
		return dataSection;
	}

}
