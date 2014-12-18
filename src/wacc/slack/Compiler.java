package wacc.slack;

import java.io.IOException;
import java.io.InputStream;

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

	public Compiler() {
		
	}

	public String compile(InputStream is, int optimisationLevel) throws Exception {
	
		WaccAST ast = buildAST(is);

		if (!ErrorRecords.getInstance().isErrorFree()) {
			ErrorRecordPrinter erp = new ErrorRecordPrinter(
					ErrorRecords.getInstance(), System.out);
			erp.print();
			System.exit(ErrorRecords.getInstance().getExitCode());
		}
		
		return new CompileProgramAST((ProgramAST)ast).compile(optimisationLevel);
	}

	public WaccAST buildAST(InputStream is) throws IOException {
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

		return ast;
	}	

}
