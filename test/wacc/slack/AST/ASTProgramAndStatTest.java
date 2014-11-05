package wacc.slack.AST;


import org.junit.Test;


public class ASTProgramAndStatTest extends ASTTest {

	// STATEMENTS
	
	@Test
	public void simpleSkipAST() {
		statementTestAssert("skip", "skip");
	}
	
	@Test
	public void simpleReadAST() {
		statementTestAssert("read a", "read a");
	}
	
	@Test
	public void simpleExitAST() {
		statementTestAssert("exit 7", "exit 7");
	}
	
	@Test
	public void simpleFreeAST() {
		statementTestAssert("free 6", "free 6");
	}

	@Test
	public void simpleReturnAST() {
		statementTestAssert("return 6", "return 6");
	}
	
	@Test
	public void simplePrintAST() {
		statementTestAssert("print 6", "print 6");
	}
	
	@Test
	public void simplePrintlnAST() {
		statementTestAssert("println 6", "println 6");
	}
	
	@Test
	public void simpleIfAST() {
		statementTestAssert("if true then skip else skip fi", "if true\n\tskip\nelse\n\tskip");
	}
	
	@Test
	public void IfWithListsOfStatsAST() {
		programTestAssert("begin if true then skip;exit 7 else skip; exit 7 fi end", "start:\n\tif true then  skip exit 7 else  skip exit 7\nend");
	}
	
	@Test
	public void WhileWithMultipleStats() {
		programTestAssert("begin while true do skip;exit 7 done end", "start:\n\twhile (true)\n\t\tskip\n\t\texit 7\nend");
	}

	@Test
	public void multiSkipExitAST() {
		programTestAssert("begin skip; skip; skip; skip;exit 7 end",
				"start:"
			  + "\n\tskip"
			  + "\n\tskip"
			  + "\n\tskip"
			  + "\n\tskip"
			  + "\n\texit 7"
			  + "\nend");
	}
	
	// FUNCTION DEFINITION

	@Test
	public void simpleFunctionDeclarationAST() {
		functionTestAssert("int foo() is return 1 end",
				         "int foo()"
				       + "\n\treturn 1");
	}
	
	// EXPRS
	
	@Test
	public void simpleIntLiter() {
		exprTestAssert("124352", "124352");
	}
	
	@Test
	public void simpleBoolLiter() {
		exprTestAssert("true", "true");
	}
	
	@Test
	public void simpleCharLiter() {
		exprTestAssert("'a'", "'a'");
	}

	@Test
	public void simpleStringLiter() {
		exprTestAssert("\"HelloWorld\"", "\"HelloWorld\"");
	}
	
	// TODO: Implement PairLiter class properly and implement test - look at spec, it's just null
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
		exprTestAssert("-1", "-(1)");
	}
	
	@Test
	public void simpleBinaryOp() {
		exprTestAssert("2 * 6", "(2 * 6)");
	}
	
	@Test
	public void simpleBracketsExpr() {
		exprTestAssert("(1)", "1");
	}
}
