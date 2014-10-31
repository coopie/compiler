package wacc.slack.AST;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import antlr.WaccParser.ArgListContext;
import antlr.WaccParser.ArrayElemContext;
import antlr.WaccParser.ArrayLiterContext;
import antlr.WaccParser.ArrayTypeContext;
import antlr.WaccParser.AssignLhsContext;
import antlr.WaccParser.AssignRhsContext;
import antlr.WaccParser.BaseTypeContext;
import antlr.WaccParser.BinaryOperContext;
import antlr.WaccParser.BoolLiterContext;
import antlr.WaccParser.ExprContext;
import antlr.WaccParser.FuncContext;
import antlr.WaccParser.IntLiterContext;
import antlr.WaccParser.IntSignContext;
import antlr.WaccParser.PairElemContext;
import antlr.WaccParser.PairElemTypeContext;
import antlr.WaccParser.PairLiterContext;
import antlr.WaccParser.PairTypeContext;
import antlr.WaccParser.ParamContext;
import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.ProgramContext;
import antlr.WaccParser.StatContext;
import antlr.WaccParser.TypeContext;
import antlr.WaccParser.UnaryOperContext;
import antlr.WaccParserVisitor;

public class ASTBuilder implements WaccParserVisitor<WaccAST> {

	@Override
	public WaccAST visit(@NotNull ParseTree arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitChildren(@NotNull RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitErrorNode(@NotNull ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitTerminal(@NotNull TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitIntSign(IntSignContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitAssignRhs(AssignRhsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitArgList(ArgListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitArrayType(ArrayTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitParam(ParamContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitPairElem(PairElemContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitArrayElem(ArrayElemContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitBinaryOper(BinaryOperContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitParamList(ParamListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatAST visitStat(StatContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitBoolLiter(BoolLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitUnaryOper(UnaryOperContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitBaseType(BaseTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitProgram(ProgramContext ctx) {
		// TODO Auto-generated method stub
		return new ProgramAST(ctx.func(), ctx.stat());
	}

	@Override
	public WaccAST visitPairType(PairTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitArrayLiter(ArrayLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitAssignLhs(AssignLhsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitPairLiter(PairLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FuncAST visitFunc(FuncContext ctx) {
		// TODO Auto-generated method stub		
		return new FuncAST(ctx.type(), ctx.IDENT(), ctx.paramList(), ctx.stat());
	}

	@Override
	public WaccAST visitIntLiter(IntLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaccAST visitPairElemType(PairElemTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
