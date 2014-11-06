package wacc.slack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.symbolTable.FuncIdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.expectations.FunctionCallExpectation;
import wacc.slack.errorHandling.expectations.FunctionReturnTypeExpectation;

public class ErrorRecordsTest {

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
		records.record(new ErrorRecord(){

			@Override
			public String getMessage() {
				return "a serious error";
			}

			@Override
			public FilePosition getFilePosition() {
				return null;
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
		
		records.addExpectation(new FunctionCallExpectation("intStringFunc", args));
		
		assertThat(records.isErrorFree(),is(false));	
	}
	
	@Test
	public void canCheckTrueParamsFunctionExpectations() {
		List<Type> params = new LinkedList<Type>(Arrays.asList(
								BaseType.T_int,
								BaseType.T_string
							));
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,params,null));
		
		records.addExpectation(new FunctionCallExpectation("intStringFunc", args));
		
		assertThat(records.isErrorFree(),is(true));	
	}
	
	@Test
	public void canCheckTrueReturnType() {
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,new LinkedList<Type>(),null));
		records.addExpectation(new FunctionReturnTypeExpectation("intStringFunc", BaseType.T_int));
		
		assertThat(records.isErrorFree(),is(true));	
	}
	
	@Test
	public void canCheckFalseReturnType() {
		
		records.scope.insert("intStringFunc", new FuncIdentInfo(BaseType.T_int,new LinkedList<Type>(),null));
		records.addExpectation(new FunctionReturnTypeExpectation("intStringFunc", BaseType.T_char));
		
		assertThat(records.isErrorFree(),is(false));	
	}
}
