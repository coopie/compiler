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

public class ASTTest {

	@Test
	public void simpleSkipAST() {
		simpleTestAssert("begin skip end", "start:\n\tskip\nend");
	}
	
	@Test
	public void skipExitAST() {
		simpleTestAssert("begin skip; exit 7 end", "start:\n\texit 7\nend");
	}

	@Test
	public void simpleFreeAST() {
		simpleTestAssert("begin free 6 end", "start:\n\tfree 6\nend");
	}
	
	@Test
	public void multiSkipExitAST() {
		simpleTestAssert("begin skip; skip; skip; skip;exit 7 end", "start:\n\tskip\n\tskip\n\tskip\n\tskip\n\texit 7\nend");
	}

	@Test
	public void simpleFunctionDeclarationAST() {
		simpleTestAssert("begin int foo() is return 1 end skip end",
				         "start:\nint foo():\nreturn 1\nend\nskip\nend");
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
			ParseTree tree = parser.program();
			
			return (WaccAST)tree.accept(new ASTBuilder());
			
			
		} catch(IOException i) {
			// can't really happen
		}
		return null;
	}
}
