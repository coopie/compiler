package wacc.slack.AST;
/*
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;

public class StatAssignemntAndScopingTests extends StatASTTest {

	@Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
	
	@SuppressWarnings("unchecked")
	SymbolTable<IdentInfo> table = context.mock(SymbolTable.class);
	//TODO: neeeds work
	@Test
	public void simpleIntAssign() {
		astBuilder.setSymbolTable(table);
		
		context.checking(new Expectations(){{
    		never(table).initializeNewScope();
    		exactly(1).of(table).insert("a", new IdentInfo(BaseType.T_int,null));
    	}});
      
		
		simpleTestAssert("begin int a = 1", "start:\n\tskip\nend");
	}
	

}*/
