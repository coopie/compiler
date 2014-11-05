package wacc.slack.AST.Expr;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import wacc.slack.FilePosition;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class BinaryExprAST implements ExprAST {

	private final BinaryOp binaryOp;
	private final ExprAST exprL, exprR;
	private final boolean typesCheck;
	private final FilePosition filePos;

	
	public BinaryExprAST(BinaryOp binaryOp, ExprAST exprL, ExprAST exprR, FilePosition filePos) {
		this.binaryOp = binaryOp;
		this.exprL = exprL;
		this.exprR = exprR;
		this.typesCheck = checkTypes();
		this.filePos = filePos;
	}
	
	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public void accept(ASTVisitor<?> visitor) {
		exprL.accept(visitor);
		visitor.visit(this);
		exprR.accept(visitor);
	}
	
	@Override
	public Type getType() {
		return binaryOp.getType();
	}
	
	private boolean checkTypes() {
		switch (binaryOp) {
		case MUL:
		case DIV:
		case MOD:
		case PLUS:
		case MINUS:
		case GT:
		case GTE:
		case LT:
		case LTE:
			return exprL.getType() == BaseType.T_int && exprR.getType() == BaseType.T_int;
		case EQ:
		case NEQ:
			return exprL.getType() == exprR.getType();
		case AND:
		case OR:
			return exprL.getType() == BaseType.T_bool && exprR.getType() == BaseType.T_bool;
		default:
			throw new RuntimeException("not supported BiaryOP");
		}
	}

	public BinaryOp getBinaryOp() {
		return binaryOp;
	}
	
	public ExprAST getExprL() {
		return exprL;
	}
	
	public ExprAST getExprR() {
		return exprR;
	}
	
	@Override
	public String toString() {
		return exprL.toString() + " " + binaryOp.toString() + " " + exprR.toString();
	}
	
	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(exprL,exprR));
	}
}
