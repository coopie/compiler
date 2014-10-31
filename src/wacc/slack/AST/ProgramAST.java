package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import antlr.WaccParser.FuncContext;
import antlr.WaccParser.StatContext;

public class ProgramAST implements WaccAST {
	
	private final List<FuncAST> func = new LinkedList<>();
//	private final StatAST stat;
	
	public ProgramAST(List<FuncContext> func2, StatContext stat2) {
		
		
	}
	

}
