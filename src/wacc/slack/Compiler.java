package wacc.slack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import antlr.WaccLexer;
import antlr.WaccParser;

import java.io.FileInputStream;
import java.io.InputStream;

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
		ParseTree tree = parser.program();
		System.out.println(tree.toStringTree(parser));
		int errors = parser.getNumberOfSyntaxErrors();
		if (errors > 0) {
			System.exit(-1);
		}
	}

}
