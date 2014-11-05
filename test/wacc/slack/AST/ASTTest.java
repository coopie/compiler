package wacc.slack.AST;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import wacc.slack.AST.visitors.PrintingVisitor;
import antlr.WaccLexer;
import antlr.WaccParser;

public class ASTTest {

	public ASTTest() {
		super();
	}
	
	protected void statementTestAssert(String in, String expectedOut) {
		WaccAST ast = getStat(in);
		PrintingVisitor p = new PrintingVisitor();
		ast.accept(p);
		
		System.out.println(p);
		assertEquals(expectedOut, p.toString());
	}
	
	protected void exprTestAssert(String in, String expectedOut) {
		WaccAST ast = getExpr(in);
		PrintingVisitor p = new PrintingVisitor();
		ast.accept(p);
		
		System.out.println(p);
		assertEquals(expectedOut, p.toString());
	}
	
	protected void programTestAssert(String in, String expectedOut) {
		WaccAST ast = getAST(in);
		PrintingVisitor p = new PrintingVisitor();
		ast.accept(p);
		
		System.out.println(p);
		assertEquals(expectedOut, p.toString());
	}
	
	protected void functionTestAssert(String in, String expectedOut) {
		WaccAST ast = getFunc(in);
		PrintingVisitor p = new PrintingVisitor();
		ast.accept(p);
		
		System.out.println(p);
		assertEquals(expectedOut, p.toString());
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
	
	private WaccAST getStat(String s) {
		return getRule(s, new ParserManipulator() {
			@Override
			public ParseTree manipulate(WaccParser p) {
				return p.stat();
			}
		});
	}
	
	private WaccAST getExpr(String s) {
		return getRule(s, new ParserManipulator() {
			
			@Override
			public ParseTree manipulate(WaccParser p) {
				return p.expr();
			}
		});
	}
	
	private WaccAST getFunc(String s) {
		return getRule(s, new ParserManipulator() {
			
			@Override
			public ParseTree manipulate(WaccParser p) {
				return p.func();
			}
		});
	}
	
	private WaccAST getRule(String s, ParserManipulator pm) {
		try{
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
			WaccLexer lexer = new WaccLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			WaccParser parser = new WaccParser(tokens);
			ParseTree tree = pm.manipulate(parser);
			
			return (WaccAST)tree.accept(astBuilder);
			
			
		} catch(IOException i) {
			// can't really happen
		}
		return null;
	}

}