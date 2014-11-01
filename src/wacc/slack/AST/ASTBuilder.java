package wacc.slack.AST;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import wacc.slack.AST.literals.BoolLiterAST;
import wacc.slack.AST.statements.ExitStatementAST;
import wacc.slack.AST.statements.FreeStatementAST;
import wacc.slack.AST.statements.IfStatementAST;
import wacc.slack.AST.statements.PrintStatementAST;
import wacc.slack.AST.statements.PrintlnStatementAST;
import wacc.slack.AST.statements.ReadStatementAST;
import wacc.slack.AST.statements.ReturnStatementAST;
import wacc.slack.AST.statements.ScopeAST;
import wacc.slack.AST.statements.SkipStatementAST;
import wacc.slack.AST.statements.WhileStatementAST;
import wacc.slack.AST.types.BaseType;
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

	// Cale
	@Override
	public WaccAST visitAssignRhs(AssignRhsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public WaccAST visitArgList(ArgListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public Type visitArrayType(ArrayTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public WaccAST visitParam(ParamContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public Assignable visitPairElem(PairElemContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public Assignable visitArrayElem(ArrayElemContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public WaccAST visitBinaryOper(BinaryOperContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
//	public List<ParamAST> visitParamList(ParamListContext ctx) { //must return WaccAST or something extending it
	public WaccAST visitParamList(ParamListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Michael 
	@Override
	public ExprAST visitExpr(ExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Timotej
	@Override
	public Type visitType(TypeContext ctx) {
		if(ctx.baseType() != null) {
			return visitBaseType(ctx.baseType());
		} else if(ctx.pairType() != null) {
			return visitPairType(ctx.pairType());
		} else if(ctx.arrayType()!= null) {
			return visitArrayType(ctx.arrayType());
		} else {
			assert false: "should not happen, one of the types should be recognized";
		}
		return null;
	}

	// Timotej
	@Override
	public StatAST visitStat(StatContext ctx) {
	//TODO: assign statements	
		List<StatAST> stats = new LinkedList<StatAST>();
	
		if(ctx.stat().size() > 1 && ctx.IF() == null) {
			for(StatContext s : ctx.stat()) {
				stats.add(visitStat(s));
			}
			return new StatAST(stats);
		} else {
			if(ctx.READ() != null) {
				return new ReadStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.EXIT() != null) {
				return new ExitStatementAST(visitExpr(ctx.expr()));
			} else if (ctx.SKIP() != null) {
				return new SkipStatementAST();
			} else if(ctx.FREE() != null) {
				return new FreeStatementAST(visitExpr(ctx.expr()));
			} else if(ctx.RETURN() != null) {
				return new ReturnStatementAST(visitExpr(ctx.expr()));
			} else if(ctx.PRINT() != null) {
				return new PrintStatementAST(visitExpr(ctx.expr()));
			} else if(ctx.PRINTLN() != null) {
				return new PrintlnStatementAST(visitExpr(ctx.expr()));
			} else if(ctx.IF() != null && ctx.THEN() != null && ctx.ELSE() != null && ctx.FI() != null) {
				return new IfStatementAST(visitExpr(ctx.expr()),visitStat(ctx.stat(0)), visitStat(ctx.stat(1)));			
			} else if(ctx.WHILE() != null && ctx.DO() != null && ctx.DONE() != null) {
				return new WhileStatementAST(visitExpr(ctx.expr()),visitStat(ctx.stat(0)));
			} else if(ctx.BEGIN() != null &&  ctx.END() != null) {
				return new ScopeAST(visitStat(ctx.stat(0)));
			} else {
				assert false: "should not happen";
			}
		}
		return null;
	}

	// Michael
	@Override
	public WaccAST visitBoolLiter(BoolLiterContext ctx) {
		// TODO Auto-generated method stub
		if (ctx.TRUE() != null) {
			return new BoolLiterAST(true);
		} else {
			return new BoolLiterAST(false);
		}
	}

	// Cale
	@Override
	public WaccAST visitUnaryOper(UnaryOperContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Timotej
	@Override
	public Type visitBaseType(BaseTypeContext ctx) {
		if(ctx.BOOL() != null) {
			return BaseType.T_bool;
		} else if(ctx.INT() != null) {
			return BaseType.T_int;
		} else if(ctx.CHAR() != null) {
			return BaseType.T_char;
		} else if(ctx.STRING() != null) {
			return BaseType.T_string;
		} else {
			assert false: "should not happen, one of the types should be recognized";
		}
		return null;
	}

	// Timotej
	@Override
	public WaccAST visitProgram(ProgramContext ctx) {
		List<FuncAST> func = new LinkedList<>();
		
		for(FuncContext f : ctx.func()) {
			func.add(visitFunc(f));
		}
		return new ProgramAST(func, visitStat(ctx.stat()));
	}

	// Cale
	@Override
	public Type visitPairType(PairTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Cale
	@Override
	public WaccAST visitArrayLiter(ArrayLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Timotej
	@Override
	public Assignable visitAssignLhs(AssignLhsContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.IDENT() != null) {
			return  null;
		} else if(ctx.arrayElem() != null) {
			return visitArrayElem(ctx.arrayElem());
		} else if(ctx.pairElem() != null) {
			return visitPairElem(ctx.pairElem());
		} else {
			assert false: "should not happen, one of the elems should be recognized";
		}
		return null;
	}

	// Cale
	@Override
	public WaccAST visitPairLiter(PairLiterContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	// Michael
	@Override
	public FuncAST visitFunc(FuncContext ctx) {	
		StatAST stat = visitStat(ctx.stat());
		String ident = ctx.IDENT().getText();
		
		List<ParamAST> paramList = null;//visitParamList(ctx.paramList());
		
		return new FuncAST(ident, paramList, stat);
	}

	// Michael
	@Override
	public WaccAST visitIntLiter(IntLiterContext ctx) { 		
		return null;
	}

	// Cale
	@Override
	public WaccAST visitPairElemType(PairElemTypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
