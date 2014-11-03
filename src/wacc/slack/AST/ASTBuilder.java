package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.literals.ArrayLiter;
import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.CharLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.statements.BeginEndAST;
import wacc.slack.AST.statements.ExitStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;
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

public class ASTBuilder implements WaccParserVisitor<ParseTreeReturnable> {

	@Override
	public ParseTreeReturnable visit(@NotNull ParseTree arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTreeReturnable visitChildren(@NotNull RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTreeReturnable visitErrorNode(@NotNull ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTreeReturnable visitTerminal(@NotNull TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	// Michael
	@Override
	public IntLiter visitIntSign(IntSignContext ctx) {
		if (ctx.MINUS() != null) {
			return new IntLiter(-1);
		} else {
			return new IntLiter(1);
		}
	}

	// Cale
	@Override
	public ParseTreeReturnable visitAssignRhs(AssignRhsContext ctx) {
		if (ctx.expr() != null) {
			return visitExpr(ctx.expr(0));
		} else if (ctx.arrayLiter() != null) {
			return visitArrayLiter(ctx.arrayLiter());
		} else if (ctx.pairElem() != null) {
			return visitPairElem(ctx.pairElem());
		} else if (ctx.NEWPAIR() != null) {
			ExprAST expr1 = visitExpr(ctx.expr(0));
			ExprAST expr2 = visitExpr(ctx.expr(1));
			return new NewPairAST(expr1, expr2);
		} else if (ctx.CALL() != null) {
			String ident = ctx.IDENT().getText();
			ArgList argList = visitArgList(ctx.argList());
			return new CallAST(ident, argList);
		} else {
			assert false : "should not happen, one of the assignments should be recognized";
		}
		return null;
	}

	// Cale
	@Override
	public ArgList visitArgList(ArgListContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}

		return new ArgList(exprList);
	}

	// Cale
	@Override
	public Type visitArrayType(ArrayTypeContext ctx) {
		if (ctx.baseType() != null) {
			return visitBaseType(ctx.baseType());
		} else if (ctx.pairType() != null) {
			return visitPairType(ctx.pairType());
		} else if (ctx.arrayType() != null) {
			return visitArrayType(ctx.arrayType());
		} else {
			assert false : "should not happen, one of the types should be recognized";
		}
		return null;
	}

	// Cale
	@Override
	public Param visitParam(ParamContext ctx) {
		String ident = ctx.IDENT().getText();
		Type type = visitType(ctx.type());
		return new Param(ident, type);
	}

	// Cale
	@Override
	public Assignable visitPairElem(PairElemContext ctx) {
		if (ctx.FST() != null) {
			return new FstAST(visitExpr(ctx.expr()));
		} else if (ctx.SND() != null) {
			return new SndAST(visitExpr(ctx.expr()));
		} else {
			assert false: "should not happen, can only start with fst or snd";
		}
		return null;
	}

	// Cale
	@Override
	public ArrayElem visitArrayElem(ArrayElemContext ctx) {
		String ident = ctx.IDENT().getText();
		ExprAST expr = visitExpr(ctx.expr());
		return new ArrayElem(ident, expr);
	}

	// Michael
	@Override
	public BinaryOp visitBinaryOper(BinaryOperContext ctx) {
		if (ctx.MUL() != null) {
			return BinaryOp.MUL;
		} else if (ctx.DIV() != null) {
			return BinaryOp.DIV;
		} else if (ctx.MOD() != null) {
			return BinaryOp.MOD;
		} else if (ctx.PLUS() != null) {
			return BinaryOp.PLUS;
		} else if (ctx.MINUS() != null) {
			return BinaryOp.MINUS;
		} else if (ctx.GT() != null) {
			return BinaryOp.GT;
		} else if (ctx.GTE() != null) {
			return BinaryOp.GTE;
		} else if (ctx.LT() != null) {
			return BinaryOp.LT;
		} else if (ctx.LTE() != null) {
			return BinaryOp.LTE;
		} else if (ctx.EQ() != null) {
			return BinaryOp.EQ;
		} else if (ctx.NEQ() != null) {
			return BinaryOp.NEQ;
		} else if (ctx.AND() != null) {
			return BinaryOp.AND;
		} else if (ctx.OR() != null) {
			return BinaryOp.OR;
		} else {
			assert false : "should not happen, one of the operators should be recognized";
		}
		return null;
	}

	// Cale
	@Override
	public ParamList visitParamList(ParamListContext ctx) {
		List<Param> paramList = new LinkedList<>();
		
		for (ParamContext p : ctx.param()) {
			paramList.add(visitParam(p));
		}
		
		return new ParamList(paramList);
	}

	// Michael
	@Override
	public ExprAST visitExpr(ExprContext ctx) {
		if (ctx.intLiter() != null) {
			return new ValueExprAST(visitIntLiter(ctx.intLiter()));
			//return new ExprAST(visitIntLiter(ctx.intLiter()));
		} else if (ctx.boolLiter() != null) {
			return new ValueExprAST(visitBoolLiter(ctx.boolLiter()));
			//return new ExprAST(visitBoolLiter(ctx.boolLiter()));
		} else if (ctx.CHAR_LTR() != null) {
			return new ValueExprAST(new CharLiter(ctx.CHAR_LTR().getText()));
			//return new ExprAST(new CharLiter(ctx.CHAR_LTR().getText()));
		} else if (ctx.STRING_LTR() != null) {
			return new ValueExprAST(new StringLiter(ctx.STRING_LTR().getText()));
			//return new ExprAST(new StringLiter(ctx.STRING_LTR().getText()));
		} else if (ctx.pairLiter() != null) {
			return new ValueExprAST(visitPairLiter(ctx.pairLiter()));
			//return new ExprAST(visitPairLiter(ctx.pairLiter()));
		} else if (ctx.IDENT() != null) {
			return null;
		} else if (ctx.arrayElem() != null) {
			// Need to fix this
			// visitArrayElem returns Assignable
			// need to return LiterAST
			// return new ExprAST(visitArrayElem(ctx.arrayElem()));
			return null;
		} else if (ctx.unaryOper() != null) {
			return new UnaryExprAST(visitUnaryOper(ctx.unaryOper()), visitExpr(ctx.expr(0)));
			//return new ExprAST(visitExpr(ctx.expr(0)),
			//		visitUnaryOper(ctx.unaryOper()));
		} else if (ctx.binaryOper() != null) {
			return new BinaryExprAST(visitBinaryOper(ctx.binaryOper()), visitExpr(ctx.expr(0)), visitExpr(ctx.expr(1)));
			//return new ExprAST(visitExpr(ctx.expr(0)), visitExpr(ctx.expr(1)),
			//		visitBinaryOper(ctx.binaryOper()));
		} else {
			return visitExpr(ctx.expr(0));
			//return new ExprAST(visitExpr(ctx.expr(0)));
		}
	}

	// Timotej
	@Override
	public Type visitType(TypeContext ctx) {
		if (ctx.baseType() != null) {
			return visitBaseType(ctx.baseType());
		} else if (ctx.pairType() != null) {
			return visitPairType(ctx.pairType());
		} else if (ctx.arrayType() != null) {
			return visitArrayType(ctx.arrayType());
		} else {
			assert false : "should not happen, one of the types should be recognized";
		}
		return null;
	}

	// Timotej
	@Override
	public StatAST visitStat(StatContext ctx) {
		List<StatAST> stats = new LinkedList<StatAST>();

		if (ctx.stat().size() > 1 && ctx.IF() == null) {
			for (StatContext s : ctx.stat()) {
				stats.add(visitStat(s));
			}
			return new StatAST(stats);
		} else {
			if (ctx.READ() != null) {
				return new ReadStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.EXIT() != null) {
				return new ExitStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.SKIP() != null) {
				return new SkipStatementAST();
			} else if (ctx.FREE() != null) {
				return new FreeStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.RETURN() != null) {
				return new ReturnStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.PRINT() != null) {
				return new PrintStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.PRINTLN() != null) {
				return new PrintlnStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.IF() != null && ctx.THEN() != null
					&& ctx.ELSE() != null && ctx.FI() != null) {
				return new IfStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)), visitStat(ctx.stat(1)));
			} else if (ctx.WHILE() != null && ctx.DO() != null
					&& ctx.DONE() != null) {
				return new WhileStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)));
			} else if (ctx.BEGIN() != null && ctx.END() != null) {
				return new BeginEndAST(visitStat(ctx.stat(0)));
			} else {
				assert false : "should not happen";
			}
		}
		return null;
	}

	// Michael
	@Override
	public Liter visitBoolLiter(BoolLiterContext ctx) {
		if (ctx.TRUE() != null) {
			return new BoolLiter(true);
		} else {
			return new BoolLiter(false);
		}
	}

	// Cale
	@Override
	public UnaryOp visitUnaryOper(UnaryOperContext ctx) {
		if (ctx.NOT() != null) {
			return UnaryOp.NOT;
		} else if (ctx.MINUS() != null) {
			return UnaryOp.MINUS;
		} else if (ctx.LEN() != null) {
			return UnaryOp.LEN;
		} else if (ctx.ORD() != null) {
			return UnaryOp.ORD;
		} else if (ctx.CHR() != null) {
			return UnaryOp.CHR;
		} else {
			assert false: "should not happen, one of the operators should be recognized";
		}
		return null;
	}

	// Timotej
	@Override
	public Type visitBaseType(BaseTypeContext ctx) {
		if (ctx.BOOL() != null) {
			return BaseType.T_bool;
		} else if (ctx.INT() != null) {
			return BaseType.T_int;
		} else if (ctx.CHAR() != null) {
			return BaseType.T_char;
		} else if (ctx.STRING() != null) {
			return BaseType.T_string;
		} else {
			assert false : "should not happen, one of the types should be recognized";
		}
		return null;
	}

	// Timotej
	@Override
	public ParseTreeReturnable visitProgram(ProgramContext ctx) {
		List<FuncAST> func = new LinkedList<>();

		for (FuncContext f : ctx.func()) {
			func.add(visitFunc(f));
		}
		return new ProgramAST(func, visitStat(ctx.stat()));
	}

	// Cale
	@Override
	public PairType visitPairType(PairTypeContext ctx) {
		if (ctx.PAIR() != null) {
			Type fst = visitPairElemType(ctx.pairElemType(0));
			Type snd = visitPairElemType(ctx.pairElemType(1));
			return new PairType(fst, snd);
		} else {
			assert false: "must start with keyword pair";
		}
		return null;
	}

	// Cale
	@Override
	public ArrayLiter visitArrayLiter(ArrayLiterContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}

		return new ArrayLiter(exprList);
	}

	// Timotej
	@Override
	public Assignable visitAssignLhs(AssignLhsContext ctx) {
		if (ctx.IDENT() != null) {
			return null;
		} else if (ctx.arrayElem() != null) {
			return visitArrayElem(ctx.arrayElem());
		} else if (ctx.pairElem() != null) {
			return visitPairElem(ctx.pairElem());
		} else {
			assert false : "should not happen, one of the elems should be recognized";
		}
		return null;
	}

	// Cale
	@Override
	public Liter visitPairLiter(PairLiterContext ctx) {
		return new PairLiter();
	}

	// Michael
	@Override
	public FuncAST visitFunc(FuncContext ctx) {
		StatAST stat = visitStat(ctx.stat());
		String ident = ctx.IDENT().getText();
		
		List<Param> paramList = null;
		
		if(ctx.paramList() != null) {
			paramList = visitParamList(ctx.paramList()).getParamList();
		}

		return new FuncAST(ident, paramList, stat);
	}

	// Michael
	@Override
	public Liter visitIntLiter(IntLiterContext ctx) {
		IntLiter sign = new IntLiter(1);
		if(ctx.intSign() != null) {
			sign = visitIntSign(ctx.intSign());
		}
		int intLiter = Integer.parseInt(ctx.INTEGER().getText());
		return new IntLiter(intLiter * sign.getInt());
	}

	// Cale
	@Override
	public Type visitPairElemType(PairElemTypeContext ctx) {
		if (ctx.baseType() != null) {
			return visitBaseType(ctx.baseType());
		} else if (ctx.arrayType() != null) {
			return visitArrayType(ctx.arrayType());
		} else if (ctx.PAIR() != null) {
			return BaseType.T_pair;
		} else {
			assert false: "should not happen, one of the pair elem types should be recognized";
		}
		return null;
	}

}
