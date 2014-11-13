package wacc.slack.AST;


import org.junit.Test;


public class ASTProgramAndStatTest extends ASTTest {

	// STATEMENTS
	
	@Test
	public void simpleSkipAST() {
		statementTestAssert("skip", "\nskip");
	}
	
	@Test
	public void simpleReadAST() {
		statementTestAssert("read a", "read a");
	}
	
	@Test
	public void simpleExitAST() {
		statementTestAssert("exit 7", "\nexit 7");
	}
	
	@Test
	public void simpleFreeAST() {
		statementTestAssert("free 6", "\nfree 6");
	}

	@Test
	public void simpleReturnAST() {
		statementTestAssert("return 6", "\nreturn 6");
	}
	
	@Test
	public void simplePrintAST() {
		statementTestAssert("print 6", "\nprint 6");
	}
	
	@Test
	public void simplePrintlnAST() {
		statementTestAssert("println 6", "\nprintln 6");
	}
	
	@Test
	public void simpleCallAST() {
		statementTestAssert("int a = call add(1,2)", "\na = add(1 2 )");
	}
	
	@Test
	public void simpleIfAST() {
		statementTestAssert("if true then skip else skip fi",
				  "\nif true"
				+ "\n\tskip"
				+ "\nelse"
				+ "\n\tskip");
	}
	
	@Test
	public void fstOnLHS() {
		statementTestAssert("pair(int, int) p = newpair(1,2);"
				          + "fst p = 3", 
				            "\np = newpair(1, 2)"
				          + "\nfst p = 3");
	}
	
	@Test
	public void sndOnLHS() {
		statementTestAssert("pair(int, int) p = newpair(1,2);"
				          + "snd p = 3", 
				            "\np = newpair(1, 2)"
				          + "\nsnd p = 3");
	}
	
	@Test
	public void arrayElemTest() {
		statementTestAssert("int[] something = [1];"
				          + "something[0] = 2",
				            "\nsomething = [1 ]"
				          + "\nsomething[0] = 2");
	}
	
	@Test
	public void arrayElemWithComplicatedExpr() {
		statementTestAssert("int[] something = [1];"
		                  + "something[something[0] - 1] = 2",
		                    "\nsomething = [1 ]"
		                  + "\nsomething[(something[0] - 1)] = 2");
	}
	
	@Test
	public void IfWithListsOfStatsAST() {
		programTestAssert("begin if true then skip;exit 7 else skip; exit 7 fi end", 
				"start:"
				+ "\n\tif true"
				+ "\n\t\tskip"
				+ "\n\t\texit 7"
				+ "\n\telse"
				+ "\n\t\tskip"
				+ "\n\t\texit 7"
				+ "\nend");
	}
	
	@Test
	public void WhileWithMultipleStats() {
		programTestAssert("begin while true do skip;exit 7 done end",
				  "start:"
				+ "\n\twhile (true)"
				+ "\n\t\tskip"
				+ "\n\t\texit 7"
				+ "\nend");
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
		exprTestAssert("'a'", "a");
	}

	@Test
	public void simpleStringLiter() {
		exprTestAssert("\"HelloWorld\"", "\"HelloWorld\"");
	}
	
	@Test
	public void simplePairLiter() {
		exprTestAssert("null", "NULL");
	}
	
	@Test
	public void simpleIdent() {
		statementTestAssert("int a = 0", "\na = 0");
	}
	
	@Test
	public void simpleArrayElemStat() {
		statementTestAssert("int[] a = [1]", "\na = [1 ]");
	}
	
	@Test
	public void newPairTest() {
		statementTestAssert("pair(int, char) a = newpair(1, 'a')", "\na = newpair(1, a)");
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
