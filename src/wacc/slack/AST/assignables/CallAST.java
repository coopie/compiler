package wacc.slack.AST.assignables;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class CallAST implements AssignRHS {
	
	private final String ident;
	private final ArgList argList;
	private final FilePosition filePos;
	
	public CallAST(String ident, ArgList argList, FilePosition filePos) {
		this.ident = ident;
		this.argList = argList;
		this.filePos = filePos;
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public void accept(ASTVisitor<?> visitor) {
		visitor.visit(this);
	}
	
	public String getIdent() {
		return ident;
	}
	
	public ArgList getArgList() {
		return argList;
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>();
	}

}
