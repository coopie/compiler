package wacc.slack.AST;

import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class ProgramAST implements WaccAST {
	
	private final List<FuncAST> func; 
	private final StatAST stat;
	private final FilePosition filePos;
	
	public ProgramAST(List<FuncAST> func, StatAST stat, FilePosition filePos) {
		this.func = func;
		this.stat = stat;
		this.filePos = filePos;
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
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
