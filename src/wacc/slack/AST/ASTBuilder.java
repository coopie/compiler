package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import wacc.slack.ErrorRecord;
import wacc.slack.ErrorRecords;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.assignables.ArrayElem;
import wacc.slack.AST.assignables.AssignRHS;
import wacc.slack.AST.assignables.Assignable;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.assignables.NewPairAST;
import wacc.slack.AST.assignables.Param;
import wacc.slack.AST.assignables.ParamList;
import wacc.slack.AST.assignables.SndAST;
import wacc.slack.AST.assignables.VariableAST;
import wacc.slack.AST.literals.ArrayLiter;
import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.BoolLiter;
import wacc.slack.AST.literals.CharLiter;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.Liter;
import wacc.slack.AST.literals.PairLiter;
import wacc.slack.AST.literals.StringLiter;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.statements.AssignStatAST;
import wacc.slack.AST.statements.BeginEndAST;
import wacc.slack.AST.statements.ExitStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.StatAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
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

	private SymbolTable<IdentInfo> scope;
	
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

	@Override
	public IntLiter visitIntSign(IntSignContext ctx) {
		if (ctx.MINUS() != null) {
			return new IntLiter(-1, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			return new IntLiter(1, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		}
	}

	@Override
	public AssignRHS visitAssignRhs(AssignRhsContext ctx) {
		if (ctx.expr() != null) {
			return visitExpr(ctx.expr(0));
		} else if (ctx.arrayLiter() != null) {
			return visitArrayLiter(ctx.arrayLiter());
		} else if (ctx.pairElem() != null) {
			return visitPairElem(ctx.pairElem());
		} else if (ctx.NEWPAIR() != null) {
			ExprAST expr1 = visitExpr(ctx.expr(0));
			ExprAST expr2 = visitExpr(ctx.expr(1));
			return new NewPairAST(expr1, expr2, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.CALL() != null) {
			String ident = ctx.IDENT().getText();
			ArgList argList = visitArgList(ctx.argList());
			return new CallAST(ident, argList, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			assert false : "should not happen, one of the assignments should be recognized";
		}
		return null;
	}

	@Override
	public ArgList visitArgList(ArgListContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}

		return new ArgList(exprList, ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

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

	@Override
	public Param visitParam(ParamContext ctx) {
		String ident = ctx.IDENT().getText();
		Type type = visitType(ctx.type());
		return new Param(ident, type, ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

	@Override
	public Assignable visitPairElem(PairElemContext ctx) {
		if (ctx.FST() != null) {
			return new FstAST(visitExpr(ctx.expr()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.SND() != null) {
			return new SndAST(visitExpr(ctx.expr()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			assert false: "should not happen, can only start with fst or snd";
		}
		return null;
	}

	@Override
	public ArrayElem visitArrayElem(ArrayElemContext ctx) {
		String ident = ctx.IDENT().getText();
		ExprAST expr = visitExpr(ctx.expr());
		return new ArrayElem(ident, expr, ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

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

	@Override
	public ParamList visitParamList(ParamListContext ctx) {
		List<Param> paramList = new LinkedList<>();
		
		for (ParamContext p : ctx.param()) {
			paramList.add(visitParam(p));
		}
		
		return new ParamList(paramList, ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

	@Override
	public ExprAST visitExpr(ExprContext ctx) {
		if (ctx.intLiter() != null) {
			return new ValueExprAST(visitIntLiter(ctx.intLiter()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.boolLiter() != null) {
			return new ValueExprAST(visitBoolLiter(ctx.boolLiter()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.CHAR_LTR() != null) {
			return new ValueExprAST(new CharLiter(ctx.CHAR_LTR().getText()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.STRING_LTR() != null) {
			return new ValueExprAST(new StringLiter(ctx.STRING_LTR().getText()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.pairLiter() != null) {
			return new ValueExprAST(visitPairLiter(ctx.pairLiter()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.IDENT() != null) {
			return null;
		} else if (ctx.arrayElem() != null) {
			return new ValueExprAST(visitArrayElem(ctx.arrayElem()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.unaryOper() != null) {
			return new UnaryExprAST(visitUnaryOper(ctx.unaryOper()), visitExpr(ctx.expr(0)), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else if (ctx.binaryOper() != null) {
			return new BinaryExprAST(visitBinaryOper(ctx.binaryOper()), visitExpr(ctx.expr(0)), visitExpr(ctx.expr(1)), ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			return visitExpr(ctx.expr(0));
		}
	}

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

	@Override
	public StatAST visitStat(StatContext ctx) {
		StatAST stat;
		List<StatAST> stats = new LinkedList<StatAST>();

		final int line = ctx.start.getLine();
		final int col = ctx.start.getCharPositionInLine();
		
		if (ctx.stat().size() > 1 && ctx.IF() == null) {
			for (StatContext s : ctx.stat()) {
				stats.add(visitStat(s));
			}
			return new StatAST(stats, line, col);
		} else {
			if (ctx.READ() != null) {
				stat = new ReadStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.EXIT() != null) {
				stat = new ExitStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.SKIP() != null) {
				stat = new SkipStatementAST(line, col);
			} else if (ctx.ASSIGN() != null) {
				AssignRHS rhs = visitAssignRhs(ctx.assignRhs());
				if(ctx.type() != null && ctx.IDENT() != null) {
					scope.insert(ctx.IDENT().getText(), new IdentInfo(visitType(ctx.type())));
					stat = new AssignStatAST(new VariableAST(ctx.IDENT().getText()), rhs, line, col);
				} else if(ctx.assignLhs() != null) {
					final Assignable a = visitAssignLhs(ctx.assignLhs());
					if(scope.lookup(a.getName()) !=  null) {
						ErrorRecords.getInstance().record(new ErrorRecord(){

							@Override
							public String getMessage() {
								return "variable: " + a.getName() + "not defined in scope";
							}

							@Override
							public int getLineNumber() {
								return line;
							}
							
						});
					}
					stat = new AssignStatAST(a, rhs, line, col);
				} else {throw new RuntimeException("shouldn't happen, can't recognize Assign stat rule");}
			} else if (ctx.FREE() != null) {
				stat = new FreeStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.RETURN() != null) {
				stat = new ReturnStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.PRINT() != null) {
				stat = new PrintStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.PRINTLN() != null) {
				stat = new PrintlnStatementAST(visitExpr(ctx.expr()), line, col);
			} else if (ctx.IF() != null && ctx.THEN() != null
					&& ctx.ELSE() != null && ctx.FI() != null) {
				scope = scope.initializeNewScope();
				stat = new IfStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)), visitStat(ctx.stat(1)), line, col);
				scope = scope.popScope();
			} else if (ctx.WHILE() != null && ctx.DO() != null
					&& ctx.DONE() != null) {
				scope = scope.initializeNewScope();
				stat = new WhileStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)), line, col);
				scope = scope.popScope();
			} else if (ctx.BEGIN() != null && ctx.END() != null) {
				scope = scope.initializeNewScope();
				stat = new BeginEndAST(visitStat(ctx.stat(0)), line, col);
				scope = scope.popScope();
			} else {
				throw new RuntimeException("shouldn't happen, can't recognize stat rule");
			}
		}
		return stat;
	}

	@Override
	public Liter visitBoolLiter(BoolLiterContext ctx) {
		if (ctx.TRUE() != null) {
			return new BoolLiter(true, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			return new BoolLiter(false, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		}
	}

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

	@Override
	public ParseTreeReturnable visitProgram(ProgramContext ctx) {
		scope = new SymbolTable<>();
		List<FuncAST> func = new LinkedList<>();

		for (FuncContext f : ctx.func()) {
			func.add(visitFunc(f));
		}
		return new ProgramAST(func, visitStat(ctx.stat()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

	@Override
	public PairType visitPairType(PairTypeContext ctx) {
		if (ctx.PAIR() != null) {
			Type fst = visitPairElemType(ctx.pairElemType(0));
			Type snd = visitPairElemType(ctx.pairElemType(1));
			return new PairType(fst, snd, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		} else {
			assert false: "must start with keyword pair";
		}
		return null;
	}

	@Override
	public ArrayLiter visitArrayLiter(ArrayLiterContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}
		//TODO: lookup null in the symbol table to get the type
		return new ArrayLiter(exprList, ctx.start.getLine(), ctx.start.getCharPositionInLine(),null);

	}

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

	@Override
	public Liter visitPairLiter(PairLiterContext ctx) {
		return new PairLiter(ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

	@Override
	public FuncAST visitFunc(FuncContext ctx) {
		List<Param> paramList = null;
		
		if(ctx.paramList() != null) {
			paramList = visitParamList(ctx.paramList()).getParamList();
		}

		return new FuncAST(visitType(ctx.type()),
				ctx.IDENT().getText(),
				paramList,
				visitStat(ctx.stat()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

	@Override
	public Liter visitIntLiter(IntLiterContext ctx) {
		IntLiter sign = new IntLiter(1, ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if(ctx.intSign() != null) {
			sign = visitIntSign(ctx.intSign());
		}
		int intLiter = Integer.parseInt(ctx.INTEGER().getText());
		return new IntLiter(intLiter * sign.getInt(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
	}

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
	
	//for mocking symbol table
	void setSymbolTable(SymbolTable<IdentInfo> t) {
		scope = t;
	}
}
