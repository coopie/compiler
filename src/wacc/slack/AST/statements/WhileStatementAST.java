package wacc.slack.AST.statements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.visitors.ASTVisitor;
import wacc.slack.errorHandling.errorRecords.ErrorRecord;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;

public class WhileStatementAST extends StatAST implements WaccAST {

	private final ExprAST cond;
	private final StatAST body;
	public WhileStatementAST(ExprAST exprAST, StatAST body, final FilePosition filePos) {
		super(filePos);
		this.cond = exprAST;
		this.body = body;
		
		if(!cond.getType().equals(BaseType.T_bool)){
			ErrorRecords.getInstance().record(new ErrorRecord(){

				@Override
				public String getMessage() {
					return "condition for while statement doesn't evaluate to bool";
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
	
	public ExprAST getCond() {
		return cond;
	}
	
	public StatAST getBody() {
		return body;
	}
	
	@Override
	public String toString() {
	return "while";
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(cond,body));
	}
}
