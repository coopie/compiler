package wacc.slack.errorHandling.errorRecords;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;

public class SemanticAnalysisTest {

	ErrorRecords records = ErrorRecords.getInstance(true);
	private ArgList args = new ArgList	(new LinkedList<ExprAST>(Arrays.asList(
			 new ValueExprAST(new IntLiter(1,null),null),
			 new ValueExprAST(new StringLiter("s",null),null)
			)),null);;
	
	@Before
	public void init() {
		records.scope = new SymbolTable<>();
	}
	
	
	@Test
	public void canCheckWhileCondIsNotBool() {

		new WhileStatementAST(new ValueExprAST(new IntLiter(1, null), null), new SkipStatementAST(null), null);
		
		assertThat(records.isErrorFree(), is(false));
	}
	
	@Test
	public void canCheckWhileCondIsBool() {

		new WhileStatementAST(new ValueExprAST(
				new BoolLiter("true", null), null), new SkipStatementAST(null), null);
		
		assertThat(records.isErrorFree(), is(true));
	}
	
	@Test
	public void canCheckIfStatementCondIsNotBool() {
		
		new IfStatementAST(new ValueExprAST(
				new IntLiter(1, null), null), new SkipStatementAST(null), new SkipStatementAST(null), null);
		
		assertThat(records.isErrorFree(), is(false));
	}
	
	@Test
	public void canCheckIfStatementCondIsBool() {
		
		new IfStatementAST(new ValueExprAST(
				new BoolLiter("true", null), null), new SkipStatementAST(null), new SkipStatementAST(null), null);
		
		assertThat(records.isErrorFree(), is(true));
	}

}
