package wacc.slack.AST;


import org.junit.Test;


public class ASTProgramAndStatTest extends StatASTTest {

	// STATEMENTS
	
	@Test
	public void simpleSkipAST() {
		statementTestAssert("skip", "skip");
	}
	
	@Test
	public void simpleFreeAST() {
		programTestAssert("begin free 6 end", "start:\n\tfree 6\nend");
	}

	@Test
	public void simpleReturnAST() {
		programTestAssert("begin return 6 end", "start:\n\treturn 6\nend");
	}
	
	@Test
	public void simplePrintAST() {
		programTestAssert("begin print 6 end", "start:\n\tprint 6\nend");
	}
	
	@Test
	public void simplePrintlnAST() {
		programTestAssert("begin println 6 end", "start:\n\tprintln 6\nend");
	}
	
	@Test
	public void simpleIfAST() {
		programTestAssert("begin if true then skip else skip fi end", "start:\n\tif true then  skip else  skip\nend");
	}
	
	@Test
	public void IfWithListsOfStatsAST() {
		programTestAssert("begin if true then skip;exit 7 else skip; exit 7 fi end", "start:\n\tif true then  skip exit 7 else  skip exit 7\nend");
	}
	
	@Test
	public void WhileWithMultipleStats() {
		programTestAssert("begin while true do skip;exit 7 done end", "start:\n\twhile true do  skip exit 7\nend");
	}

	@Test
	public void multiSkipExitAST() {
		programTestAssert("begin skip; skip; skip; skip;exit 7 end", "start:\n\tskip\n\tskip\n\tskip\n\tskip\n\texit 7\nend");
	}
	
	// FUNCTION DEFINITION

	@Test
	public void simpleFunctionDeclarationAST() {
		programTestAssert("begin int foo() is return 1 end skip end",
				         "start:\nint foo():\nreturn 1\nend\n\tskip\nend");
	}
	
	// Expr
	
	@Test
	public void simpleIntLiter() {
		programTestAssert("begin print 1 end", "start:\n\tprint 1\nend");
	}
	
	@Test
	public void simpleBoolLiter() {
		programTestAssert("begin print true end", "start:\n\tprint true\nend");
	}
	
	// TODO: I don't think this should do this. Check the printing of char-liters (we should only save a not 'a')
	@Test
	public void simpleCharLiter() {
		programTestAssert("begin print 'a' end", "start:\n\tprint 'a'\nend");
	}

	// TODO: I don't think this should do this. Check the printing of string-liters (we should only save HelloWorld not "HelloWorld")
	@Test
	public void simpleStringLiter() {
		programTestAssert("begin print \"HelloWorld\" end", "start:\n\tprint \"HelloWorld\"\nend");
	}
	
	// TODO: Implement PairLiter class properly and implement test
	@Test
	public void simplePairLiter() {
	}
	
	// TODO: Implement Ident class properly and implement test
	@Test
	public void simpleIdent() {
	}
	
	// TODO: Implement ArrayElem class properly and implement test
	@Test
	public void simpleArrayElem() {
	}
	
	@Test
	public void simpleUnaryOp() {
		programTestAssert("begin print -1 end", "start:\n\tprint - 1\nend");
	}
	
	@Test
	public void simpleBinaryOp() {
		programTestAssert("begin print 2 * 6 end", "start:\n\tprint 2 * 6\nend");
	}
	
	@Test
	public void simpleBracketsExpr() {
		programTestAssert("begin print (1) end", "start:\n\tprint 1\nend");
	}
}
