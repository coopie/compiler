package wacc.slack.AST.Expr;

import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.visitors.ASTVisitor;

public class BinaryExprAST implements ExprAST {

	private final BinaryOp binaryOp;
	private final ExprAST exprL, exprR;
	private final boolean typesCheck;
	private final int linePos, charPos;

	
	public BinaryExprAST(BinaryOp binaryOp, ExprAST exprL, ExprAST exprR, int linePos, int charPos) {
		this.binaryOp = binaryOp;
		this.exprL = exprL;
		this.exprR = exprR;

		this.typesCheck = checkTypes();
		
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
		exprL.accept(visitor);
		exprR.accept(visitor);
		visitor.visit(this);
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
}
