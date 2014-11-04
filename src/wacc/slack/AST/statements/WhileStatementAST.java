package wacc.slack.AST.statements;

import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.visitors.ASTVisitor;

public class WhileStatementAST extends StatAST {

	private final ExprAST cond;
	private final StatAST body;

	public WhileStatementAST(ExprAST exprAST, StatAST body, FilePosition filePos) {
		super(filePos);
		addStat(this);
		this.cond = exprAST;
		this.body = body;
	}

	@Override 
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
	
	public ExprAST getCond() {
		return cond;
	}
	
	public StatAST getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		String body = "";
		
		for(StatAST st : this.body) {
			body += " " + st.toString();
		}
		
		return "while " + cond.toString() + " do " + body;
	}
}
