package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class FuncAST implements WaccAST{	
	
	private final String ident;
	private final StatAST stat;
	private final List<Param> paramList;
	private final int linePos, charPos;

	public FuncAST(String ident, List<Param> paramList2, StatAST stat, int linePos, int charPos) {
		this.ident = ident;
		this.paramList = paramList2;
		this.stat = stat;
		this.linePos = linePos;
		this.charPos = charPos;
	}

	@Override
	public int getLine() {
		return linePos;
	}
	
	@Override
	public int getCharColumn() {
		return charPos;
	}
	
	@Override
	public String toString() {
		String output = ident + "(";
		if(paramList != null) {
			for(Param p : paramList) {
				output = p.toString() + ", ";
			}
			if(output.endsWith(", ")) {
				output.substring(0, output.length() - 3);
			}
		}
		output += "):\n";
		output += stat;
		
		return output;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public String getIdent() {
		return ident;
	}

	public List<Param> getParamList() {
		return paramList;
	}
	
	public StatAST getStat() {
		return stat;
	}
}
