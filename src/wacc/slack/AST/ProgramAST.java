package wacc.slack.AST;

import java.util.List;

import wacc.slack.AST.visitors.ASTVisitor;

public class ProgramAST implements WaccAST {
	
	private final List<FuncAST> func; 
	private final StatAST stat;
	
	public ProgramAST(List<FuncAST> func2, StatAST statAST) {
		func = func2;
		stat = statAST;
	}

	@Override
	public int getPosition() {
		return 0;
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
