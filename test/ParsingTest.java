import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import antlr.WaccLexer;
import antlr.WaccParser;


public class ParsingTest {

	@Test
	public void basicSkip() {
		assertThat(parseProgram("begin skip end"), is("(program begin (stat skip) end <EOF>)"));
	}
	
	@Test
	public void basicExit() {
		assertThat(parseProgram("begin exit 7 end"), is("(program begin (stat exit (expr (intLiter 7))) end <EOF>)"));
	}
	
	@Test
	public void basicAddition() {
		assertThat(parseExpr("7 + 7"), is("(expr (expr (intLiter 7)) (binaryOper +) (expr (intLiter 7)))"));
	}
	
	@Test
	public void arrayElementWithMultiplcation() {
		assertThat(parseExpr("a[1 + 9] + 7"), is("(expr (expr (arrayElem a [ (expr (expr (intLiter 1)) (binaryOper +) (expr (intLiter 9))) ])) (binaryOper +) (expr (intLiter 7)))"));
	}
	
	@Test
	public void inlineDeclarationAndAssigment() {
		assertThat(parseStatement("int[] a = [1,3424,4353]"), is("(stat (type (arrayType (baseType int) [ ])) a = (assignRhs (arrayLiter [ (expr (intLiter 1)) , (expr (intLiter 3424)) , (expr (intLiter 4353)) ])))"));
		
	}
	
	private String parseProgram(String s) {
			WaccParser p = getParser(s);
			ParseTree tree = p.program();
			return tree.toStringTree(p);
	}
	
	private String parseExpr(String s) {
		WaccParser p = getParser(s);
		ParseTree tree = p.expr();
		return tree.toStringTree(p);
	}
	
	private String parseStatement(String s) {
		WaccParser p = getParser(s);
		ParseTree tree = p.stat();
		return tree.toStringTree(p);
	}
	
	private WaccParser getParser(String s) {
		try{
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
			WaccLexer lexer = new WaccLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			return new WaccParser(tokens);
			
		} catch(IOException i) {
			// can't really happen
		}
		return null;
	}
	

}
