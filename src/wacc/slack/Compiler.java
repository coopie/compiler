package wacc.slack;

import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import wacc.slack.errorHandling.errorRecords.ErrorRecordPrinter;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.GenerateAssembly;
import antlr.WaccLexer;
import antlr.WaccParser;

public class Compiler {

	public static void main(String[] args) throws Exception {
		String inputFile = null;
		if (args.length > 0) {
			inputFile = args[0];
		}
		InputStream is = System.in;
		if (inputFile != null) {
			is = new FileInputStream(inputFile);
		}
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
		
		GenerateAssembly psuedoInstructionVisitor = new GenerateAssembly();
		
		for(PseudoInstruction i : ast.accept(new IntermediateCodeGenerator())) {
			System.out.print(i.accept(psuedoInstructionVisitor));
		}
		

		System.exit(0);
	}

}
