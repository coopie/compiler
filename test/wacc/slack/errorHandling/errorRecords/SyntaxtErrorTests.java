package wacc.slack.errorHandling.errorRecords;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.ParserManipulator;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import antlr.WaccLexer;
import antlr.WaccParser;

public class SyntaxtErrorTests {
	
	ErrorRecords records = ErrorRecords.getInstance(true);
	
	
	@Test
	public void IfWithListsOfStatsAST() {
		getAST("begin ifi true then skip;exit 7 else skip; exit 7 fi end");
		
		assertThat(records.isErrorFree(),is(false));
	}
	
	
	
	
	protected ASTBuilder astBuilder = new ASTBuilder();
	
	private WaccAST getAST(String s) {
		return getRule(s, new ParserManipulator() {
			
			@Override
			public ParseTree manipulate(WaccParser p) {
				return p.program();
			}
		});
	}
	
	private WaccAST getRule(String s, ParserManipulator pm) {
		try{
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
			WaccLexer lexer = new WaccLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			WaccParser parser = new WaccParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(new WaccSyntaxtErrorListner());
			ParseTree tree = pm.manipulate(parser);
				
			return (WaccAST)tree.accept(astBuilder);
			
			
		} catch(IOException i) {
			// can't really happen
		}
		return null;
	}
}
