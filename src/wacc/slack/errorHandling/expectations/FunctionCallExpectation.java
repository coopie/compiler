package wacc.slack.errorHandling.expectations;

import java.util.Iterator;
import java.util.List;

import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.assignables.Param;
import wacc.slack.AST.types.Type;


public class FunctionCallExpectation implements WaccExpectation<List<Type>> {

	private final String ident;
	private final ArgList args;

	public FunctionCallExpectation(String ident, ArgList args) {
		this.ident = ident;
		this.args = args;
	}

	public String getIdent() {
		return ident;
	}
	
	@Override
	public boolean check(List<Type> params) {
		boolean b = true;
		Iterator<Type> ps = params.iterator();
		Iterator<ExprAST> as = args.iterator();
		
		while(ps.hasNext() && as.hasNext()) 
		{
			b &= ps.next().equals(as.next().getType());
		}
		
		b &= !ps.hasNext() && !as.hasNext();
		
		return b;
	}

}
