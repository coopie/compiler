package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class FuncAST implements WaccAST{	
	
	private final String ident;
	private final StatAST stat;
	private final List<ParamAST> paramList;

	public FuncAST(String ident, List<ParamAST> paramList2, StatAST stat) {
		this.ident = ident;
		this.paramList = paramList2;
		this.stat = stat;
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public String getIdent() {
		return ident;
	}

	public List<ParamAST> getParamList() {
		return paramList;
	}
	
	public StatAST getStat() {
		return stat;
	}
}
