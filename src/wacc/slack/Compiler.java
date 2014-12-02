package wacc.slack;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import wacc.slack.errorHandling.errorRecords.ErrorRecordPrinter;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.visitors.GenerateAssembly;
import wacc.slack.instructions.visitors.GenerateAssemblyBuilder;
import wacc.slack.instructions.visitors.SimpleRegisterAllocator;
import wacc.slack.instructions.visitors.TemporaryReplacer;
import wacc.slack.interferenceGraph.InterferenceGraph;
import wacc.slack.interferenceGraph.InterferenceGraphColourer;
import antlr.WaccLexer;
import antlr.WaccParser;

public class Compiler {

	public static void main(String[] args) throws Exception {
		String inputFile = null;
		String outputFile = null;
		Compiler compiler = new Compiler();

		if (args.length > 0) {
			inputFile = args[0];

			if (!args[0].endsWith(".wacc")) {
				throw new RuntimeException(
						"File to compile must end with \".wacc\"");
			}

			outputFile = args[0].substring(0, args[0].length() - 5) + ".s";
		}

		InputStream is = System.in;

		if (inputFile != null) {
			is = new FileInputStream(inputFile);
		}

		PrintStream out = new PrintStream(new File(outputFile));

		out.print(compiler.compile(is));

		out.print('\n');
		out.close();

		System.exit(0);
	}

	public Compiler() {

	}

	public String compile(InputStream is) throws Exception {

		ANTLRInputStream input = new ANTLRInputStream(is);
		WaccLexer lexer = new WaccLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		WaccParser parser = new WaccParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new WaccSyntaxtErrorListner());
		ParseTree tree = parser.program();
		WaccAST ast = null;
		ASTBuilder builder = new ASTBuilder();

		try {
			ast = (WaccAST) tree.accept(builder);
		} catch (NullPointerException e) {
			ErrorRecords.getInstance().setScope(builder.getScope());
			if (ErrorRecords.getInstance().isErrorFree()) {
				throw e;
			}
		}

		ErrorRecords.getInstance().setScope(builder.getScope());

		if (!ErrorRecords.getInstance().isErrorFree()) {
			ErrorRecordPrinter erp = new ErrorRecordPrinter(
					ErrorRecords.getInstance(), System.out);
			erp.print();
			System.exit(ErrorRecords.getInstance().getExitCode());
		}

		Deque<PseudoInstruction> intermediateCode = ast
				.accept(new IntermediateCodeGenerator());

		int optimisationLevel = 0;
		intermediateCode = doOptimisations(intermediateCode, optimisationLevel);

		GenerateAssembly psuedoInstructionVisitor = new GenerateAssemblyBuilder()
		// .ignoringTemporaries()
				.withOptimisationLevel(optimisationLevel).make();

		String output = "";

		// insert pseudoinstruction code change from temporary register
		// allocation here

		// realCode =

		//
		for (PseudoInstruction i : intermediateCode) {
			output += i.accept(psuedoInstructionVisitor);
		}

		return output;
	}

	private Deque<PseudoInstruction> doOptimisations(
			Deque<PseudoInstruction> intermediateCode, int optimizationLevel) {
		if (optimizationLevel == 0) {
			return simpleRegisterAllocation(intermediateCode);
		}

		Deque<PseudoInstruction> codeWithoutTemporaries = new LinkedList<>();
		final Map<Register, ArmRegister> mapping = new HashMap<>();
		ControlFlowGraph cfg = new ControlFlowGraph(intermediateCode);
		InterferenceGraph ig = new InterferenceGraph(cfg);
		InterferenceGraphColourer igc = new InterferenceGraphColourer(ig);
		igc.generateTemporaryRegisterMappings(mapping);

		for (PseudoInstruction i : intermediateCode) {
			codeWithoutTemporaries.addAll(i.accept(new TemporaryReplacer(
					mapping)));
		}
		return codeWithoutTemporaries;
	}

	private Deque<PseudoInstruction> simpleRegisterAllocation(
			Deque<PseudoInstruction> intermediateCode) {
		Deque<PseudoInstruction> finalCode = new ArrayDeque<PseudoInstruction>();
		for (PseudoInstruction ps : intermediateCode) {
			finalCode.addAll(ps.accept(new SimpleRegisterAllocator()));
			finalCode.add(new AssemblerDirective("\n")); // for debugging
															// purposes
		}
		return finalCode;
	}

}
