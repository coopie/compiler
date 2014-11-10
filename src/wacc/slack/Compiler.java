package wacc.slack;
import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.WaccAST;
import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
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
		WaccAST ast = (WaccAST)tree.accept(new ASTBuilder());
		
		if(!ErrorRecords.getInstance().isErrorFree()) {
			System.exit(ErrorRecords.getInstance().getExitCode());
		}
		
		System.exit(0);
	}

}
