package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

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
import wacc.slack.AST.symbolTable.FuncIdentInfo;
import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.PairType;
import wacc.slack.AST.types.Type;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.IllegalOperationError;
import wacc.slack.errorHandling.errorRecords.UndeclaredVariableError;
import wacc.slack.errorHandling.expectations.FunctionCallExpectation;
import antlr.WaccParser.ArgListContext;
import antlr.WaccParser.ArrayElemContext;
import antlr.WaccParser.ArrayElemExprContext;
import antlr.WaccParser.ArrayLiterContext;
import antlr.WaccParser.ArrayTypeContext;
import antlr.WaccParser.AssignLhsContext;
import antlr.WaccParser.AssignRhsContext;
import antlr.WaccParser.AssignStatContext;
import antlr.WaccParser.BaseTypeContext;
import antlr.WaccParser.BeginStatContext;
import antlr.WaccParser.BinaryOpContext;
import antlr.WaccParser.BoolLiterContext;
import antlr.WaccParser.BoolLiterExprContext;
import antlr.WaccParser.CharLiterExprContext;
import antlr.WaccParser.ExitStatContext;
import antlr.WaccParser.ExprContext;
import antlr.WaccParser.ExprInParenthesesExprContext;
import antlr.WaccParser.FreeStatContext;
import antlr.WaccParser.FuncContext;
import antlr.WaccParser.IdentExprContext;
import antlr.WaccParser.IfStatContext;
import antlr.WaccParser.IntLiterContext;
import antlr.WaccParser.IntLiterExprContext;
import antlr.WaccParser.IntSignContext;
import antlr.WaccParser.PairElemContext;
import antlr.WaccParser.PairElemTypeContext;
import antlr.WaccParser.PairLiterContext;
import antlr.WaccParser.PairLiterExprContext;
import antlr.WaccParser.PairTypeContext;
import antlr.WaccParser.ParamContext;
import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.PrintStatContext;
import antlr.WaccParser.PrintlnStatContext;
import antlr.WaccParser.ProgramContext;
import antlr.WaccParser.ReadStatContext;
import antlr.WaccParser.ReturnStatContext;
import antlr.WaccParser.SkipStatContext;
import antlr.WaccParser.StatContext;
import antlr.WaccParser.StatListContext;
import antlr.WaccParser.StringLiterExprContext;
import antlr.WaccParser.TypeContext;
import antlr.WaccParser.UnaryExprContext;
import antlr.WaccParser.UnaryOperContext;
import antlr.WaccParser.WhileStatContext;
import antlr.WaccParserVisitor;

public class ASTBuilder implements WaccParserVisitor<ParseTreeReturnable> {

	private SymbolTable<IdentInfo> scope;
	private String currentFunction = null; // equals null if visitor is not in a
											// function

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
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		if (ctx.MINUS() != null) {
			return new IntLiter(-1, filePos);
		} else {
			return new IntLiter(1, filePos);
		}
	}

	@Override
	public AssignRHS visitAssignRhs(AssignRhsContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());

		if (ctx.arrayLiter() != null) {
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
			ErrorRecords.getInstance().addExpectation(
					new FunctionCallExpectation(ident, argList));
			return new CallAST(ident, argList, filePos);
		} else if (ctx.expr() != null) {
			return visitExpr(ctx.expr(0));
		} else {
			assert false : "should not happen, one of the assignments should be recognized";
		}
		return null;
	}

	@Override
	public ArgList visitArgList(ArgListContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();
		FilePosition filePos;
		if (ctx != null) {
			filePos = new FilePosition(ctx.start.getLine(),
					ctx.start.getCharPositionInLine());
			for (ExprContext e : ctx.expr()) {
				exprList.add(visitExpr(e));
			}
		} else {
			filePos = new FilePosition(-1, -1);
		}
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new Param(ident, type, filePos);
	}

	@Override
	public Assignable visitPairElem(PairElemContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		if (ctx.FST() != null) {
			return new FstAST(visitExpr(ctx.expr()), filePos);
		} else if (ctx.SND() != null) {
			return new SndAST(visitExpr(ctx.expr()), filePos);
		} else {
			assert false : "should not happen, can only start with fst or snd";
		}
		return null;
	}

	@Override
	public ArrayElemAST visitArrayElem(ArrayElemContext ctx) {
		String ident = ctx.IDENT().getText();
		ExprAST expr = visitExpr(ctx.expr());
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ArrayElemAST(ident, expr, scope, filePos);
	}

	@Override
	public ParamList visitParamList(ParamListContext ctx) {
		List<Param> paramList = new LinkedList<>();

		for (ParamContext p : ctx.param()) {
			paramList.add(visitParam(p));
		}

		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ParamList(paramList, filePos);
	}

	private ExprAST visitExpr(ExprContext ctx) {
		if (ctx == null) {
			// TODO: might not be a problem to record
			// ErrorRecords.getInstance().record();
			return new ValueExprAST(new IntLiter(0, new FilePosition(-1, -1)),
					new FilePosition(-1, -1));
		}
		return (ExprAST) ctx.accept(this);
	}

	@Override
	public ExprAST visitCharLiterExpr(CharLiterExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(new CharLiter(ctx.CHAR_LTR().getText()),
				filePos);
	}

	@Override
	public ExprAST visitIdentExpr(IdentExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		if (scope.lookup(ctx.IDENT().getText()) != null) {
			return new VariableAST(ctx.IDENT().getText(), scope, filePos);
		} else {
			ErrorRecords.getInstance().record(
					new UndeclaredVariableError(filePos, ctx.IDENT().getText()));
			return new ValueExprAST(new IntLiter(0, filePos), filePos);
		}
	}

	@Override
	public ExprAST visitStringLiterExpr(StringLiterExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(new StringLiter(ctx.STRING_LTR().getText(),
				filePos), filePos);
	}

	@Override
	public ExprAST visitBinaryOp(BinaryOpContext ctx) {
		BinaryOp op = BinaryOp.MUL;
		if (ctx.MUL() != null) {
			op = BinaryOp.MUL;
		} else if (ctx.DIV() != null) {
			op = BinaryOp.DIV;
		} else if (ctx.PLUS() != null) {
			op = BinaryOp.PLUS;
		} else if (ctx.MINUS() != null) {
			op = BinaryOp.MINUS;
		} else if (ctx.MOD() != null) {
			op = BinaryOp.MOD;
		} else if (ctx.EQ() != null) {
			op = BinaryOp.EQ;
		} else if (ctx.NEQ() != null) {
			op = BinaryOp.NEQ;
		} else if (ctx.LT() != null) {
			op = BinaryOp.LT;
		} else if (ctx.LTE() != null) {
			op = BinaryOp.LTE;
		} else if (ctx.GT() != null) {
			op = BinaryOp.GT;
		} else if (ctx.GTE() != null) {
			op = BinaryOp.GTE;
		} else if (ctx.AND() != null) {
			op = BinaryOp.AND;
		} else if (ctx.OR() != null) {
			op = BinaryOp.OR;
		} else {
			// Case when a operator is not seen
			op = BinaryOp.MUL;
		}

		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());

		ExprAST expr1 = visitExpr(ctx.expr(0));
		ExprAST expr2 = visitExpr(ctx.expr(1));

		if (expr1 == null) {
			expr1 = new ValueExprAST(new IntLiter(0,
					new FilePosition(-1, -1)), new FilePosition(-1, -1));
		}

		if (expr2 == null) {
			expr2 = new ValueExprAST(new IntLiter(0,
					new FilePosition(-1, -1)), new FilePosition(-1, -1));
		}

		return new BinaryExprAST(op, expr1, expr2, filePos);
	}

	@Override
	public ExprAST visitBoolLiterExpr(BoolLiterExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(visitBoolLiter(ctx.boolLiter()), filePos);
	}

	@Override
	public ExprAST visitArrayElemExpr(ArrayElemExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(visitArrayElem(ctx.arrayElem()), filePos);
	}

	@Override
	public ExprAST visitIntLiterExpr(IntLiterExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(visitIntLiter(ctx.intLiter()), filePos);
	}

	@Override
	public ExprAST visitExprInParenthesesExpr(ExprInParenthesesExprContext ctx) {
		return visitExpr(ctx.expr());
	}

	@Override
	public ExprAST visitUnaryExpr(UnaryExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		if (ctx.unaryOper() == null) {
			// TODO: might not be a problem to record
			// ErrorRecords.getInstance().record();
			return new UnaryExprAST(visitUnaryOper(ctx.unaryOper()),
					visitExpr(ctx.expr()), filePos);
		}
		return new UnaryExprAST(visitUnaryOper(ctx.unaryOper()),
				visitExpr(ctx.expr()), filePos);
	}

	@Override
	public ExprAST visitPairLiterExpr(PairLiterExprContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ValueExprAST(visitPairLiter(ctx.pairLiter()), filePos);
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
	public StatAST visitStatList(StatListContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		List<StatAST> stats = new LinkedList<>();

		for (StatContext s : ctx.stat()) {
			stats.add((StatAST) s.accept(this));
		}
		return new StatListAST(stats, filePos);
	}

	@Override
	public StatAST visitReadStat(ReadStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ReadStatementAST(visitAssignLhs(ctx.assignLhs()), filePos);
	}

	@Override
	public StatAST visitReturnStat(ReturnStatContext ctx) {

		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		IdentInfo funcInfo = scope.lookup(currentFunction);
		ExprAST expr = visitExpr(ctx.expr());
		if (funcInfo == null) { // meaning return outside a function
			ErrorRecords.getInstance().record(
					new IllegalOperationError(filePos));
		} else {
			if (!funcInfo.getType().equals(expr.getType())) {
				/*
				 * TODO: some weird error ErrorRecords.getInstance().record(new
				 * ErrorRecord(){
				 * 
				 * @Override public String getMessage() { return
				 * "return expr doesn't match function signature"; }
				 * 
				 * @Override public FilePosition getFilePosition() { return
				 * filePos; } });
				 */
			}
		}
		return new ReturnStatementAST(expr, filePos);
	}

	@Override
	public StatAST visitAssignStat(AssignStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		AssignRHS rhs = visitAssignRhs(ctx.assignRhs());
		if (ctx.type() != null && ctx.IDENT() != null) {
			final String id = ctx.IDENT().getText();
			if (scope.lookupCurrentScope(id) != null) {
				ErrorRecords.getInstance().record(
						new UndeclaredVariableError(filePos, id));
			}
			scope.insert(id, new IdentInfo(visitType(ctx.type()), filePos));// TODO:
																			// check
																			// if
																			// it
																			// is
																			// decalred
																			// already
			return new AssignStatAST(new VariableAST(ctx.IDENT().getText(),
					scope, filePos), rhs, filePos);
		} else if (ctx.assignLhs() != null) {
			final Assignable a = visitAssignLhs(ctx.assignLhs());
			if (scope.lookup(a.getName()) == null) {
				ErrorRecords.getInstance().record(
						new UndeclaredVariableError(filePos, a.getName()));
			}
			return new AssignStatAST(a, rhs, filePos);
		} else {
			throw new RuntimeException(
					"shouldn't happen, can't recognize Assign stat rule");
		}
	}

	@Override
	public ParseTreeReturnable visitPrintStat(PrintStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new PrintStatementAST(visitExpr(ctx.expr()), filePos);
	}

	@Override
	public StatAST visitFreeStat(FreeStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new FreeStatementAST(visitExpr(ctx.expr()), filePos);
	}

	@Override
	public ParseTreeReturnable visitWhileStat(WhileStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		scope = scope.initializeNewScope();
		StatAST stat = new WhileStatementAST(visitExpr(ctx.expr()),
				visitStat(ctx.stat()), filePos);
		scope = scope.popScope();
		return stat;
	}

	@Override
	public StatAST visitExitStat(ExitStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ExitStatementAST(visitExpr(ctx.expr()), filePos);
	}

	@Override
	public StatAST visitIfStat(IfStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		scope = scope.initializeNewScope();
		StatAST stat = new IfStatementAST(visitExpr(ctx.expr()),
				visitStat(ctx.stat(0)), visitStat(ctx.stat(1)), filePos);
		scope = scope.popScope();
		return stat;
	}

	@Override
	public StatAST visitSkipStat(SkipStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new SkipStatementAST(filePos);
	}

	@Override
	public ParseTreeReturnable visitPrintlnStat(PrintlnStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new PrintlnStatementAST(visitExpr(ctx.expr()), filePos);
	}

	@Override
	public StatAST visitBeginStat(BeginStatContext ctx) {
		final FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		scope = scope.initializeNewScope();
		StatAST stat = new BeginEndAST(visitStat(ctx.stat()), filePos); // TODO:
																		// improve
																		// this
		scope = scope.popScope();
		return stat;
	}

	private StatAST visitStat(StatContext ctx) {
		if (ctx == null) {
			// ErrorRecords.getInstance().record(); //TODO: might not be a
			// problem to record
			return new StatListAST(new FilePosition(-1, -1));
		}
		return (StatAST) ctx.accept(this);
	}

	@Override
	public Liter visitBoolLiter(BoolLiterContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
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
			assert false : "should not happen, one of the operators should be recognized";
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new ProgramAST(func, (StatAST) ctx.stat().accept(this), filePos);
	}

	@Override
	public PairType visitPairType(PairTypeContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		if (ctx.PAIR() != null) {
			Type fst = visitPairElemType(ctx.pairElemType(0));
			Type snd = visitPairElemType(ctx.pairElemType(1));
			return new PairType(fst, snd, filePos);
		} else {
			assert false : "must start with keyword pair";
		}
		return null;
	}

	@Override
	public ArrayLiterAST visitArrayLiter(ArrayLiterContext ctx) {
		List<ExprAST> exprList = new LinkedList<>();

		for (ExprContext e : ctx.expr()) {
			exprList.add(visitExpr(e));
		}

		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		// TODO: lookup null in the symbol table to get the type
		return new ArrayLiterAST(exprList, filePos, null);

	}

	@Override
	public Assignable visitAssignLhs(AssignLhsContext ctx) {
		if (ctx.IDENT() != null) {
			return new VariableAST(ctx.IDENT().getText(), scope,
					new FilePosition(ctx.IDENT().getSymbol().getLine(), ctx
							.IDENT().getSymbol().getCharPositionInLine()));
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
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		return new PairLiter(filePos);
	}

	@Override
	public FuncAST visitFunc(FuncContext ctx) {
		List<Param> paramList = new LinkedList<>();
		List<Type> paramTypes = new LinkedList<>();

		if (ctx.paramList() != null) {
			paramList = visitParamList(ctx.paramList()).getParamList();
		}

		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		Type returnType = visitType(ctx.type());

		currentFunction = ctx.IDENT().getText();

		// rembering top scope so I can add the function Identifier to it after
		// I add the params to the function scope and extract the types of
		// params
		SymbolTable<IdentInfo> topScope = scope;
		scope = scope.initializeNewScope();

		for (Param p : paramList) {
			paramTypes.add(p.getType());
			scope.insert(p.getIdent(), new IdentInfo(p.getType(), filePos));
		}

		topScope.insert(ctx.IDENT().getText(), new FuncIdentInfo(returnType,
				paramTypes, filePos));

		FuncAST f = new FuncAST(returnType, currentFunction, paramList,
				(StatAST) ctx.stat().accept(this), filePos);
		scope = scope.popScope();
		currentFunction = null;
		return f;
	}

	@Override
	public Liter visitIntLiter(IntLiterContext ctx) {
		FilePosition filePos = new FilePosition(ctx.start.getLine(),
				ctx.start.getCharPositionInLine());
		IntLiter sign = new IntLiter(1, filePos);
		if (ctx.intSign() != null) {
			sign = visitIntSign(ctx.intSign());
		}
		long intLiter = Long.parseLong(ctx.INTEGER().getText());
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
			assert false : "should not happen, one of the pair elem types should be recognized";
		}
		return null;
	}

	// for mocking symbol table
	void setSymbolTable(SymbolTable<IdentInfo> t) {
		scope = t;
	}

	public SymbolTable<IdentInfo> getScope() {
		return scope;
	}
}
