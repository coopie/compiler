package wacc.slack.AST;

import org.junit.Test;

public class OperatorPrecedenceTests extends ASTTest {

	public OperatorPrecedenceTests() {
		super();
	}
	
	// operator precedence at the moment:
	//  * 
	//	/ 
	//	% 
	//	+ 
	//	- 
	//	> 
	//	>= 
	//	< 
	//	<= 
	//	== 
	//	!= 
	//	&& 
	//	|| 
	
	// ARITHMETIC
	
	@Test
	public void multiplyBeatsDiv() {
		exprTestAssert("1 / 2 * 3", "(1 / (2 * 3))");
	}
		
	@Test
	public void divBeatsPlus() {
		exprTestAssert("1 % 2 / 3", "(1 % (2 / 3))");
	}
	
	@Test
	public void modBeatsPlus() {
		exprTestAssert("1 + 2 % 3","(1 + (2 % 3))");
	}
	
	@Test
	public void plusBeatsMinus() {
		exprTestAssert("1 - 2 + 3", "(1 - (2 + 3))");
	}
	
	// INEQUALITIES AND EQUALITIES
	
	@Test
	public void gtBeatsgte() {
		exprTestAssert("1 >= 2 > 3", "(1 >= (2 > 3))");
	}
	
	@Test
	public void gteBeatslt() {
		exprTestAssert("1 < 2 >= 3", "(1 < (2 >= 3))");
	}
	
	@Test
	public void ltBeatslte() {
		exprTestAssert("1 <= 2 < 3", "(1 <= (2 < 3))");
	}
	
	@Test
	public void lteBeatsneq() {
		exprTestAssert("1 != 2 <= 3", "(1 != (2 <= 3))");
	}
	
	@Test
	public void neqBeatseq() {
		exprTestAssert("1 == 2 != 3", "(1 == (2 != 3))");
	}
	
	// LOGICAL OPERATORS
	
	@Test
	public void andbeatsOr() {
		exprTestAssert("1 || 2 && 3", "(1 || (2 && 3))");
	}
}
