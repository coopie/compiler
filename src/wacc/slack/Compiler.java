package wacc.slack;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import wacc.slack.errorHandling.errorRecords.ErrorRecordPrinter;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.interferenceGraph.CompileProgramAST;
import antlr.WaccLexer;
import antlr.WaccParser;

public class Compiler {

	public static void main(String[] args) throws Exception {
		String inputFile = null;
		String outputFile = null;
		Compiler compiler = new Compiler();
		int optimisationLevel = 0;
		
		if (args.length > 0) {
			inputFile = args[0];

			if (!args[0].endsWith(".wacc")) {
				throw new RuntimeException(
						"File to compile must end with \".wacc\"");
			}

			outputFile = args[0].substring(0, args[0].length() - 5) + ".s";
			outputFile = outputFile.replaceAll(".*/", "");
			if(args.length > 1 && args[1].startsWith("-O")) {
				optimisationLevel = Integer.parseInt(args[1].substring(2));
			}
		}

		InputStream is = System.in;

		if (inputFile != null) {
			is = new FileInputStream(inputFile);
		}

		PrintStream out = new PrintStream(new File(outputFile));

		out.print(compiler.compile(is,optimisationLevel));

		out.print('\n');
		out.close();

		System.exit(0);
	}

	public Compiler() {

	}

	public String compile(InputStream is, int optimisationLevel) throws Exception {

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
		
		return new CompileProgramAST((ProgramAST)ast).compile(optimisationLevel);
	}

	

}
