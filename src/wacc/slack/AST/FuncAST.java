package wacc.slack.AST;

import java.util.List;
import wacc.slack.AST.types.Type;

public class FuncAST implements WaccAST {
	
	Type type;
	String ident;
	List<ParamAST> paramList;
	StatAST stat;

}
