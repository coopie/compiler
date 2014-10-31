package wacc.slack.AST;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.StatContext;
import antlr.WaccParser.TypeContext;
import wacc.slack.AST.types.Type;

public class FuncAST implements WaccAST{

/*	private final Type type;
	private final String ident;
	private final List<ParamAST> paramList;
	private final StatAST stat;*/
	
	public FuncAST(TypeContext type, TerminalNode ident,
			ParamListContext paramList, StatContext stat) {
		
	}
}
