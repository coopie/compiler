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
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.TypeMismatchError;

public class BinaryExprAST implements ExprAST {

	private final BinaryOp binaryOp;
	private final ExprAST exprL, exprR;
	private final boolean typesCheck;
	private final FilePosition filePos;

	public BinaryExprAST(BinaryOp binaryOp, ExprAST exprL, ExprAST exprR,
			FilePosition filePos) {
		this.binaryOp = binaryOp;
		this.exprL = exprL;
		this.exprR = exprR;
		this.typesCheck = checkTypes();
		this.filePos = filePos;

		if (!typesCheck) {
			ErrorRecords.getInstance().record(
					new TypeMismatchError(filePos, exprR.getType(), exprL
							.getType()));
		}
	}

	@Override
	public FilePosition getFilePosition() {
		return filePos;
	}

	@Override
	public <T> T accept(ASTVisitor<T> visitor) {
		return visitor.visit(this);
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
			return bothSidesHaveTypes(BaseType.T_int);
		case GT:
		case GTE:
		case LT:
		case LTE:
			return bothSidesHaveTypes(BaseType.T_int, BaseType.T_char);
		case EQ:
		case NEQ:
			return exprL.getType().equals(exprR.getType());
		case AND:
		case OR:
			return bothSidesHaveTypes(BaseType.T_bool);
		default:
			throw new RuntimeException("not supported BiaryOP");
		}
	}

	private boolean bothSidesHaveTypes(Type... types) {
		boolean typesCheck = false;
		for (Type t : types) {
			typesCheck |= exprL.getType().equals(t)
					&& exprR.getType().equals(t);
		}
		return typesCheck;
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
		return binaryOp.toString();
	}

	@Override
	public List<WaccAST> getChildren() {
		return new LinkedList<WaccAST>(Arrays.asList(exprL, exprR));
	}

}
