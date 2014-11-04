import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import wacc.slack.AST.ASTBuilder;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.PrintingVisitor;
import antlr.WaccLexer;
import antlr.WaccParser;

public class ExprTest {
	
	@Test
	public void simpleBinaryOpAST() {
		simpleTestAssert("1+1", " 1 + 1");
	}
	
	@Test
	public void simpleUnaryOpAST() {
		simpleTestAssert("!true", " ! true");
	}
	
	@Test
	public void simpleBracketTest() {
		simpleTestAssert("(1+1)", " 1 + 1");
	}
	
	private void simpleTestAssert(String in, String expectedOut) {
		WaccAST ast = getAST(in);
		PrintingVisitor p = new PrintingVisitor();
		ast.accept(p);
		
		System.out.println(p);
		assertEquals(expectedOut, p.toString());
	}
	
	private WaccAST getAST(String s) {
		try{
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
			WaccLexer lexer = new WaccLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			WaccParser parser = new WaccParser(tokens);
			ParseTree tree = parser.expr();
			
			return (WaccAST)tree.accept(new ASTBuilder());
			
			
		} catch(IOException i) {
			// can't really happen
		}
		return null;
	}
}
