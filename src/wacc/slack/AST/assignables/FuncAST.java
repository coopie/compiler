package wacc.slack.AST.assignables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.statements.StatListAST;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class FuncAST implements WaccAST{	
	
	private final String ident;
	private final StatAST stat;
	private final List<Param> paramList;
	private final FilePosition filePos;
	private final Type type;

	public FuncAST(Type type, String ident, List<Param> paramList2, StatAST stat, FilePosition filePos) {
		this.type = type;
		this.ident = ident;
		this.paramList = paramList2;
		this.stat = stat;
		this.filePos = filePos;
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}
	
	@Override
	public String toString() {
		String output = getType() + " " + ident + "(";
		if(paramList != null) {
			for(Param p : paramList) {
				output = p.toString() + ", ";
			}
			if(output.endsWith(", ")) {
				output.substring(0, output.length() - 3);
			}
		}
		output += "):\n";
		output += stat + "\n";
		output += "end";
		return output;
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
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

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(stat));
	}

	public Type getType() {
		return type;
	}
}
