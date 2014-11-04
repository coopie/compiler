package wacc.slack.AST;


import org.junit.Test;


public class ASTProgramAndStatTest extends StatASTTest {

	// STATEMENTS
	
	@Test
	public void simpleSkipAST() {
		simpleTestAssert("begin skip end", "start:\n\tskip\nend");
	}
	
	@Test
	public void simpleFreeAST() {
		simpleTestAssert("begin free 6 end", "start:\n\tfree 6\nend");
	}

	@Test
	public void simpleReturnAST() {
		simpleTestAssert("begin return 6 end", "start:\n\treturn 6\nend");
	}
	
	@Test
	public void simplePrintAST() {
		simpleTestAssert("begin print 6 end", "start:\n\tprint 6\nend");
	}
	
	@Test
	public void simplePrintlnAST() {
		simpleTestAssert("begin println 6 end", "start:\n\tprintln 6\nend");
	}
	
	@Test
	public void simpleIfAST() {
		simpleTestAssert("begin if true then skip else skip fi end", "start:\n\tif true then  skip else  skip\nend");
	}
	
	@Test
	public void IfWithListsOfStatsAST() {
		simpleTestAssert("begin if true then skip;exit 7 else skip; exit 7 fi end", "start:\n\tif true then  skip exit 7 else  skip exit 7\nend");
	}
	
	@Test
	public void WhileWithMultipleStats() {
		simpleTestAssert("begin while true do skip;exit 7 done end", "start:\n\twhile true do  skip exit 7\nend");
	}

	@Test
	public void multiSkipExitAST() {
		simpleTestAssert("begin skip; skip; skip; skip;exit 7 end", "start:\n\tskip\n\tskip\n\tskip\n\tskip\n\texit 7\nend");
	}
	
	// FUNCTION DEFINITION

	@Test
	public void simpleFunctionDeclarationAST() {
		simpleTestAssert("begin int foo() is return 1 end skip end",
				         "start:\nint foo():\nreturn 1\nend\n\tskip\nend");
	}
	
	// Expr
	
	@Test
	public void simpleIntLiter() {
		simpleTestAssert("begin print 1 end", "start:\n\tprint 1\nend");
	}
	
	@Test
	public void simpleBoolLiter() {
		simpleTestAssert("begin print true end", "start:\n\tprint true\nend");
	}
	
	// TODO: I don't think this should do this. Check the printing of char-liters (we should only save a not 'a')
	@Test
	public void simpleCharLiter() {
		simpleTestAssert("begin print 'a' end", "start:\n\tprint 'a'\nend");
	}

	// TODO: I don't think this should do this. Check the printing of string-liters (we should only save HelloWorld not "HelloWorld")
	@Test
	public void simpleStringLiter() {
		simpleTestAssert("begin print \"HelloWorld\" end", "start:\n\tprint \"HelloWorld\"\nend");
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
	
	// TODO: Implement UnaryOp class properly and implement test
	@Test
	public void simpleUnaryOp() {
	}
	
	// TODO: Implement BinaryOp class properly and implement test
	@Test
	public void simpleBinaryOp() {
	}
	
	@Test
	public void simpleBracketsExpr() {
		simpleTestAssert("begin print (1) end", "start:\n\tprint 1\nend");
	}
}
