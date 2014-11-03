package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class ProgramAST implements WaccAST {
	
	private final List<FuncAST> func; 
	private final StatAST stat;
	private final int linePos, charPos;
	
	public ProgramAST(List<FuncAST> func, StatAST stat, int linePos, int charPos) {
		this.func = func;
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
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public List<FuncAST> getFunctions() {
		return func;
	}

	public StatAST getStatements() {
		return stat;
	}
}
