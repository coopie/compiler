package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.visitors.ASTVisitor;

public class IfStatementAST extends StatAST implements WaccAST {

	private final StatAST falseStats, trueStats;
	private final ExprAST cond;
	public IfStatementAST(ExprAST exprAST, StatAST trueStats,
			StatAST falseStats, final FilePosition filePos) {
		super(filePos);
		this.cond = exprAST;
		this.trueStats = trueStats;
		this.falseStats = falseStats;
		
		if(!exprAST.getType().equals(BaseType.T_bool)){
			ErrorRecords.getInstance().record(new ErrorRecord(){

				@Override
				public String getMessage() {
					return "expression for if statement doesn't evaluate to bool";
				}

				@Override
				public FilePosition getFilePosition() {
					return filePos;
				}
				
			});
		}
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
	public StatAST getFalseStats() {
		return falseStats;
	}
	
	public ExprAST getCond() {
		return cond;
	}
	
	public StatAST getTrueStats() {
		return trueStats;
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(cond, trueStats, falseStats));
	}

	@Override
	public String toString() {
		return "if";
	}
}
