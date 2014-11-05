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
import wacc.slack.FilePosition;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArgList;
import wacc.slack.AST.assignables.ArrayElemAST;
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
import wacc.slack.AST.literals.ArrayLiterAST;
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
import wacc.slack.AST.statements.StatListAST;
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.MINUS() != null) {
			return new IntLiter(-1, filePos);
		} else {
			return new IntLiter(1, filePos);
		}
	}

	@Override
	public AssignRHS visitAssignRhs(AssignRhsContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.expr() != null) {
			return visitExpr(ctx.expr(0));
		} else if (ctx.arrayLiter() != null) {
			return visitArrayLiter(ctx.arrayLiter());
		} else if (ctx.pairElem() != null) {
			return visitPairElem(ctx.pairElem());
		} else if (ctx.NEWPAIR() != null) {
			ExprAST expr1 = visitExpr(ctx.expr(0));
			ExprAST expr2 = visitExpr(ctx.expr(1));
			return new NewPairAST(expr1, expr2, filePos);
		} else if (ctx.CALL() != null) {
			String ident = ctx.IDENT().getText();
			ArgList argList = visitArgList(ctx.argList());
			return new CallAST(ident, argList, filePos);
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

		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new ArgList(exprList, filePos);
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new Param(ident, type, filePos);
	}

	@Override
	public Assignable visitPairElem(PairElemContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.FST() != null) {
			return new FstAST(visitExpr(ctx.expr()), filePos);
		} else if (ctx.SND() != null) {
			return new SndAST(visitExpr(ctx.expr()), filePos);
		} else {
			assert false: "should not happen, can only start with fst or snd";
		}
		return null;
	}

	@Override
	public ArrayElemAST visitArrayElem(ArrayElemContext ctx) {
		String ident = ctx.IDENT().getText();
		ExprAST expr = visitExpr(ctx.expr());
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new ArrayElemAST(ident, expr, filePos);
	}

/*	@Override
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
	}*/

	@Override
	public ParamList visitParamList(ParamListContext ctx) {
		List<Param> paramList = new LinkedList<>();
		
		for (ParamContext p : ctx.param()) {
			paramList.add(visitParam(p));
		}
		
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new ParamList(paramList, filePos);
	}

	@Override
	public ExprAST visitExpr(ExprContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.intLiter() != null) {
			return new ValueExprAST(visitIntLiter(ctx.intLiter()), filePos);
		} else if (ctx.boolLiter() != null) {
			return new ValueExprAST(visitBoolLiter(ctx.boolLiter()), filePos);
		} else if (ctx.CHAR_LTR() != null) {
			return new ValueExprAST(new CharLiter(ctx.CHAR_LTR().getText()), filePos);
		} else if (ctx.STRING_LTR() != null) {
			return new ValueExprAST(new StringLiter(ctx.STRING_LTR().getText()), filePos);
		} else if (ctx.pairLiter() != null) {
			return new ValueExprAST(visitPairLiter(ctx.pairLiter()), filePos);
		} else if (ctx.IDENT() != null) {
			return null;
		} else if (ctx.arrayElem() != null) {
			return new ValueExprAST(visitArrayElem(ctx.arrayElem()), filePos);
		} else if (ctx.unaryOper() != null) {
			return new UnaryExprAST(visitUnaryOper(ctx.unaryOper()), visitExpr(ctx.expr(0)), filePos);
		} else if (ctx.MUL() != null) {
			return retBinOP(ctx,BinaryOp.MUL);
		} else if (ctx.DIV()!= null) {
			return retBinOP(ctx,BinaryOp.DIV);
		} else if (ctx.PLUS() != null) {
			return retBinOP(ctx,BinaryOp.PLUS);
		} else if (ctx.MINUS() != null) {
			return retBinOP(ctx,BinaryOp.MINUS);
		} else if (ctx.MOD() != null) {
			return retBinOP(ctx,BinaryOp.MOD);
		} else if (ctx.EQ() != null) {
			return retBinOP(ctx,BinaryOp.EQ);
		} else if (ctx.NEQ() != null) {
			return retBinOP(ctx,BinaryOp.NEQ);
		} else if (ctx.LT() != null) {
			return retBinOP(ctx,BinaryOp.LT);
		} else if (ctx.LTE() != null) {
			return retBinOP(ctx,BinaryOp.LTE);
		} else if (ctx.GT() != null) {
			return retBinOP(ctx,BinaryOp.GT);
		} else if (ctx.GTE() != null) {
			return retBinOP(ctx,BinaryOp.GTE);
		} else if (ctx.AND() != null) {
			return retBinOP(ctx,BinaryOp.AND);
		} else if (ctx.OR() != null) {
			return retBinOP(ctx,BinaryOp.OR);
		} else {
			return visitExpr(ctx.expr(0));
		}
	}
	
	private BinaryExprAST retBinOP(ExprContext ctx, BinaryOp op) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new BinaryExprAST(op, visitExpr(ctx.expr(0)), visitExpr(ctx.expr(1)), filePos);
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
		List<StatAST> stats = new LinkedList<>();

		final FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		
		if (ctx.stat().size() > 1 && ctx.IF() == null) {
			for (StatContext s : ctx.stat()) {
				stats.add(visitStat(s));
			}
			return new StatListAST(stats, filePos);
		} else {
			if (ctx.READ() != null) {
				stat = new ReadStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.EXIT() != null) {
				stat = new ExitStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.SKIP() != null) {
				stat = new SkipStatementAST(filePos);
			} else if (ctx.ASSIGN() != null) {
				AssignRHS rhs = visitAssignRhs(ctx.assignRhs());
				if(ctx.type() != null && ctx.IDENT() != null) {
					scope.insert(ctx.IDENT().getText(), new IdentInfo(visitType(ctx.type()),filePos));//TODO: check if it is decalred already
					stat = new AssignStatAST(new VariableAST(ctx.IDENT().getText(),scope,filePos), rhs, filePos);
				} else if(ctx.assignLhs() != null) {
					final Assignable a = visitAssignLhs(ctx.assignLhs());
					if(scope.lookup(a.getName()) ==  null) {
						ErrorRecords.getInstance().record(new ErrorRecord(){

							@Override
							public String getMessage() {
								return "variable: " + a.getName() + "not defined in scope";
							}

							@Override
							public FilePosition getFilePosition() {
								return filePos;
							}
						});
					}
					stat = new AssignStatAST(a, rhs, filePos);
				} else {throw new RuntimeException("shouldn't happen, can't recognize Assign stat rule");}
			} else if (ctx.FREE() != null) {
				stat = new FreeStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.RETURN() != null) {
				stat = new ReturnStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.PRINT() != null) {
				stat = new PrintStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.PRINTLN() != null) {
				stat = new PrintlnStatementAST(visitExpr(ctx.expr()), filePos);
			} else if (ctx.IF() != null && ctx.THEN() != null
					&& ctx.ELSE() != null && ctx.FI() != null) {
				scope = scope.initializeNewScope();
				stat = new IfStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)), visitStat(ctx.stat(1)), filePos);
				scope = scope.popScope();
			} else if (ctx.WHILE() != null && ctx.DO() != null
					&& ctx.DONE() != null) {
				scope = scope.initializeNewScope();
				stat = new WhileStatementAST(visitExpr(ctx.expr()),
						visitStat(ctx.stat(0)), filePos);
				scope = scope.popScope();
			} else if (ctx.BEGIN() != null && ctx.END() != null) {
				scope = scope.initializeNewScope();
				stat = new BeginEndAST(visitStat(ctx.stat(0)), filePos);
				scope = scope.popScope();
			} else {
				throw new RuntimeException("shouldn't happen, can't recognize stat rule");
			}
		}
		return stat;
	}

	@Override
	public Liter visitBoolLiter(BoolLiterContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.TRUE() != null) {
			return new BoolLiter("true", filePos);
		} else {
			return new BoolLiter("false", filePos);
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new ProgramAST(func, visitStat(ctx.stat()), filePos);
	}

	@Override
	public PairType visitPairType(PairTypeContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		if (ctx.PAIR() != null) {
			Type fst = visitPairElemType(ctx.pairElemType(0));
			Type snd = visitPairElemType(ctx.pairElemType(1));
			return new PairType(fst, snd, filePos);
		} else {
			assert false: "must start with keyword pair";
		}
		return null;
	}

	@Override
	public ArrayLiterAST visitArrayLiter(ArrayLiterContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}
		
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		//TODO: lookup null in the symbol table to get the type
		return new ArrayLiterAST(exprList, filePos, null);

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
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new PairLiter(filePos);
	}

	@Override
	public FuncAST visitFunc(FuncContext ctx) {
		List<Param> paramList = null;
		
		if(ctx.paramList() != null) {
			paramList = visitParamList(ctx.paramList()).getParamList();
		}

		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		return new FuncAST(visitType(ctx.type()),
				ctx.IDENT().getText(),
				paramList,
				visitStat(ctx.stat()), filePos);
	}

	@Override
	public Liter visitIntLiter(IntLiterContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(), ctx.start.getCharPositionInLine());
		IntLiter sign = new IntLiter(1, filePos);
		if(ctx.intSign() != null) {
			sign = visitIntSign(ctx.intSign());
		}
		int intLiter = Integer.parseInt(ctx.INTEGER().getText());
		return new IntLiter(intLiter * sign.getInt(), filePos);
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
