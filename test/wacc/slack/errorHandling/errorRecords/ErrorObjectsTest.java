package wacc.slack.errorHandling.errorRecords;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.symbolTable.FuncIdentInfo;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.errorHandling.expectations.FunctionCallExpectation;
import wacc.slack.errorHandling.expectations.FunctionReturnTypeExpectation;

// This is for testing the use if ErrorObjects ONLY
public class ErrorObjectsTest {

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
	public void canAddErrors() {
		records.record(new ErrorObject(){

			@Override
			public String getMessage() {
				return "a serious error";
			}

			@Override
			public FilePosition getFilePosition() {
				return null;
			}

			@Override
			public ErrorType getType() {
				return ErrorType.ErrorRecord;
			}
		
		});
		
		assertThat(records.iterator().next().getMessage(), is("a serious error"));
	}
	
	@Test
	public void canBeErrorFree() {
		assertThat(records.isErrorFree(),is(true));
	}
	
	@Test
	public void canCheckFalseParamsFunctionExpectations() {
		List<Type> params = new LinkedList<Type>(Arrays.asList(
								BaseType.T_char,
								BaseType.T_char
							));
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,params,null));
		
		records.addExpectation(new FunctionCallExpectation("intStringFunc", args,new FilePosition(-1,-1)));
		
		assertThat(records.isErrorFree(),is(false));	
	}
	
	@Test
	public void canCheckTrueParamsFunctionExpectations() {
		List<Type> params = new LinkedList<Type>(Arrays.asList(
								BaseType.T_int,
								new WaccArrayType(BaseType.T_char)
							));
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,params,null));
		
		records.addExpectation(new FunctionCallExpectation("intStringFunc", args,new FilePosition(-1,-1)));
		
		assertThat(records.isErrorFree(),is(true));	
	}
	
	@Test
	public void canCheckTrueReturnType() {
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,new LinkedList<Type>(),null));
		records.addExpectation(new FunctionReturnTypeExpectation("intStringFunc", BaseType.T_int,new FilePosition(-1,-1)));
		
		assertThat(records.isErrorFree(),is(true));	
	}
	
	@Test
	public void canCheckFalseReturnType() {
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,new LinkedList<Type>(),null));
		records.addExpectation(new FunctionReturnTypeExpectation("intStringFunc", BaseType.T_char,new FilePosition(-1,-1)));
		
		assertThat(records.isErrorFree(),is(false));	
	}
	
	
	
}
