// Generated from ./WaccParser.g4 by ANTLR 4.4
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WaccParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT=35, OPEN_SQ_PARENTHESES=58, LT=16, NEWPAIR=44, DO=42, CHR=10, MINUS=6, 
		SEMICOLON=22, ELSE=39, IF=37, INTEGER=2, DONE=43, NULL=56, MUL=11, FST=47, 
		STRING_LTR=60, TRUE=54, IS=28, CHAR_LTR=61, EQ=18, READ=31, NOT=7, AND=20, 
		END=29, THEN=38, LTE=17, MAP=46, EXIT=34, PLUS=5, CLOSE_PARENTHESES=24, 
		ORD=9, CALL=45, FI=40, PRINTLN=36, OPEN_PARENTHESES=23, SND=48, CHAR=51, 
		BEGIN=27, ASSIGN=26, FREE=32, COMMENT=1, INT=49, IMPORT=3, RETURN=33, 
		SKIP=30, WS=62, FILENAME=4, COMMA=25, MOD=13, OR=21, GT=14, DIV=12, LEN=8, 
		IDENT=57, BOOL=50, GTE=15, STRING=52, WHILE=41, FALSE=55, NEQ=19, CLOSE_SQ_PARENTHESES=59, 
		PAIR=53;
	public static final String[] tokenNames = {
		"<INVALID>", "COMMENT", "INTEGER", "'import'", "FILENAME", "'+'", "'-'", 
		"'!'", "'len'", "'ord'", "'chr'", "'*'", "'/'", "'%'", "'>'", "'>='", 
		"'<'", "'<='", "'=='", "'!='", "'&&'", "'||'", "';'", "'('", "')'", "','", 
		"'='", "'begin'", "'is'", "'end'", "'skip'", "'read'", "'free'", "'return'", 
		"'exit'", "'print'", "'println'", "'if'", "'then'", "'else'", "'fi'", 
		"'while'", "'do'", "'done'", "'newpair'", "'call'", "'.map'", "'fst'", 
		"'snd'", "'int'", "'bool'", "'char'", "'string'", "'pair'", "'true'", 
		"'false'", "'null'", "IDENT", "'['", "']'", "STRING_LTR", "CHAR_LTR", 
		"WS"
	};
	public static final int
		RULE_program = 0, RULE_func = 1, RULE_paramList = 2, RULE_param = 3, RULE_stat = 4, 
		RULE_assignLhs = 5, RULE_assignRhs = 6, RULE_argList = 7, RULE_pairElem = 8, 
		RULE_type = 9, RULE_baseType = 10, RULE_arrayType = 11, RULE_pairType = 12, 
		RULE_pairElemType = 13, RULE_expr = 14, RULE_intLiter = 15, RULE_intSign = 16, 
		RULE_unaryOper = 17, RULE_arrayElem = 18, RULE_boolLiter = 19, RULE_arrayLiter = 20, 
		RULE_pairLiter = 21;
	public static final String[] ruleNames = {
		"program", "func", "paramList", "param", "stat", "assignLhs", "assignRhs", 
		"argList", "pairElem", "type", "baseType", "arrayType", "pairType", "pairElemType", 
		"expr", "intLiter", "intSign", "unaryOper", "arrayElem", "boolLiter", 
		"arrayLiter", "pairLiter"
	};

	@Override
	public String getGrammarFileName() { return "WaccParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WaccParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<TerminalNode> SEMICOLON() { return getTokens(WaccParser.SEMICOLON); }
		public TerminalNode FILENAME(int i) {
			return getToken(WaccParser.FILENAME, i);
		}
		public TerminalNode EOF() { return getToken(WaccParser.EOF, 0); }
		public List<TerminalNode> FILENAME() { return getTokens(WaccParser.FILENAME); }
		public TerminalNode IMPORT(int i) {
			return getToken(WaccParser.IMPORT, i);
		}
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public List<TerminalNode> IMPORT() { return getTokens(WaccParser.IMPORT); }
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(WaccParser.SEMICOLON, i);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(44); match(IMPORT);
				setState(45); match(FILENAME);
				setState(46); match(SEMICOLON);
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(52); match(BEGIN);
			setState(56);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(53); func();
					}
					} 
				}
				setState(58);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(59); stat(0);
			setState(60); match(END);
			setState(61); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode IS() { return getToken(WaccParser.IS, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63); type();
			setState(64); match(IDENT);
			setState(65); match(OPEN_PARENTHESES);
			setState(67);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << PAIR))) != 0)) {
				{
				setState(66); paramList();
				}
			}

			setState(69); match(CLOSE_PARENTHESES);
			setState(70); match(IS);
			setState(71); stat(0);
			setState(72); match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); param();
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(75); match(COMMA);
				setState(76); param();
				}
				}
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); type();
			setState(83); match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReadStatContext extends StatContext {
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public TerminalNode READ() { return getToken(WaccParser.READ, 0); }
		public ReadStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitReadStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfStatContext extends StatContext {
		public TerminalNode FI() { return getToken(WaccParser.FI, 0); }
		public TerminalNode ELSE() { return getToken(WaccParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(WaccParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN() { return getToken(WaccParser.THEN, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public IfStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIfStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintStatContext extends StatContext {
		public TerminalNode PRINT() { return getToken(WaccParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPrintStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintlnStatContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PRINTLN() { return getToken(WaccParser.PRINTLN, 0); }
		public PrintlnStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPrintlnStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExitStatContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXIT() { return getToken(WaccParser.EXIT, 0); }
		public ExitStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitExitStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FreeStatContext extends StatContext {
		public TerminalNode FREE() { return getToken(WaccParser.FREE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FreeStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitFreeStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BeginStatContext extends StatContext {
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public BeginStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBeginStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SkipStatContext extends StatContext {
		public TerminalNode SKIP() { return getToken(WaccParser.SKIP, 0); }
		public SkipStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitSkipStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StatListContext extends StatContext {
		public List<TerminalNode> SEMICOLON() { return getTokens(WaccParser.SEMICOLON); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode SEMICOLON(int i) {
			return getToken(WaccParser.SEMICOLON, i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatListContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitStatList(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStatContext extends StatContext {
		public TerminalNode RETURN() { return getToken(WaccParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitReturnStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfWithoutElseStatContext extends StatContext {
		public TerminalNode FI() { return getToken(WaccParser.FI, 0); }
		public TerminalNode IF() { return getToken(WaccParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN() { return getToken(WaccParser.THEN, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public IfWithoutElseStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIfWithoutElseStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignStatContext extends StatContext {
		public TerminalNode ASSIGN() { return getToken(WaccParser.ASSIGN, 0); }
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public AssignStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitAssignStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileStatContext extends StatContext {
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(WaccParser.WHILE, 0); }
		public WhileStatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitWhileStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		return stat(0);
	}

	private StatContext stat(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatContext _localctx = new StatContext(_ctx, _parentState);
		StatContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_stat, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				_localctx = new SkipStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(86); match(SKIP);
				}
				break;
			case 2:
				{
				_localctx = new AssignStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87); type();
				setState(88); match(IDENT);
				setState(89); match(ASSIGN);
				setState(90); assignRhs();
				}
				break;
			case 3:
				{
				_localctx = new AssignStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92); assignLhs();
				setState(93); match(ASSIGN);
				setState(94); assignRhs();
				}
				break;
			case 4:
				{
				_localctx = new ReadStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96); match(READ);
				setState(97); assignLhs();
				}
				break;
			case 5:
				{
				_localctx = new FreeStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98); match(FREE);
				setState(99); expr(0);
				}
				break;
			case 6:
				{
				_localctx = new ReturnStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100); match(RETURN);
				setState(101); expr(0);
				}
				break;
			case 7:
				{
				_localctx = new ExitStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(102); match(EXIT);
				setState(103); expr(0);
				}
				break;
			case 8:
				{
				_localctx = new PrintStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104); match(PRINT);
				setState(105); expr(0);
				}
				break;
			case 9:
				{
				_localctx = new PrintlnStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106); match(PRINTLN);
				setState(107); expr(0);
				}
				break;
			case 10:
				{
				_localctx = new IfWithoutElseStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108); match(IF);
				setState(109); expr(0);
				setState(110); match(THEN);
				setState(111); stat(0);
				setState(112); match(FI);
				}
				break;
			case 11:
				{
				_localctx = new IfStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(114); match(IF);
				setState(115); expr(0);
				setState(116); match(THEN);
				setState(117); stat(0);
				setState(118); match(ELSE);
				setState(119); stat(0);
				setState(120); match(FI);
				}
				break;
			case 12:
				{
				_localctx = new WhileStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(122); match(WHILE);
				setState(123); expr(0);
				setState(124); match(DO);
				setState(125); stat(0);
				setState(126); match(DONE);
				}
				break;
			case 13:
				{
				_localctx = new BeginStatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(128); match(BEGIN);
				setState(129); stat(0);
				setState(130); match(END);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(147);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatListContext(new StatContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(134);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(135); match(SEMICOLON);
					setState(141);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(136); stat(0);
							setState(137); match(SEMICOLON);
							}
							} 
						}
						setState(143);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
					}
					setState(144); stat(2);
					}
					} 
				}
				setState(149);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignLhsContext extends ParserRuleContext {
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public AssignLhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignLhs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitAssignLhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignLhsContext assignLhs() throws RecognitionException {
		AssignLhsContext _localctx = new AssignLhsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignLhs);
		try {
			setState(153);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(150); match(IDENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(151); arrayElem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(152); pairElem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignRhsContext extends ParserRuleContext {
		public TerminalNode NEWPAIR() { return getToken(WaccParser.NEWPAIR, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CALL() { return getToken(WaccParser.CALL, 0); }
		public TerminalNode IDENT(int i) {
			return getToken(WaccParser.IDENT, i);
		}
		public List<TerminalNode> IDENT() { return getTokens(WaccParser.IDENT); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode MAP() { return getToken(WaccParser.MAP, 0); }
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public AssignRhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignRhs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitAssignRhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignRhsContext assignRhs() throws RecognitionException {
		AssignRhsContext _localctx = new AssignRhsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignRhs);
		int _la;
		try {
			setState(177);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(155); arrayLiter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(156); match(NEWPAIR);
				setState(157); match(OPEN_PARENTHESES);
				setState(158); expr(0);
				setState(159); match(COMMA);
				setState(160); expr(0);
				setState(161); match(CLOSE_PARENTHESES);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(163); pairElem();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(164); match(CALL);
				setState(165); match(IDENT);
				setState(166); match(OPEN_PARENTHESES);
				setState(168);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTEGER) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << OPEN_PARENTHESES) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IDENT) | (1L << STRING_LTR) | (1L << CHAR_LTR))) != 0)) {
					{
					setState(167); argList();
					}
				}

				setState(170); match(CLOSE_PARENTHESES);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(171); match(IDENT);
				setState(172); match(MAP);
				setState(173); match(OPEN_PARENTHESES);
				setState(174); match(IDENT);
				setState(175); match(CLOSE_PARENTHESES);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(176); expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179); expr(0);
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(180); match(COMMA);
				setState(181); expr(0);
				}
				}
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SND() { return getToken(WaccParser.SND, 0); }
		public TerminalNode FST() { return getToken(WaccParser.FST, 0); }
		public PairElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemContext pairElem() throws RecognitionException {
		PairElemContext _localctx = new PairElemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_pairElem);
		try {
			setState(191);
			switch (_input.LA(1)) {
			case FST:
				enterOuterAlt(_localctx, 1);
				{
				setState(187); match(FST);
				setState(188); expr(0);
				}
				break;
			case SND:
				enterOuterAlt(_localctx, 2);
				{
				setState(189); match(SND);
				setState(190); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type);
		try {
			setState(196);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(193); baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(194); arrayType(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(195); pairType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(WaccParser.BOOL, 0); }
		public TerminalNode STRING() { return getToken(WaccParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(WaccParser.CHAR, 0); }
		public TerminalNode INT() { return getToken(WaccParser.INT, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode CLOSE_SQ_PARENTHESES() { return getToken(WaccParser.CLOSE_SQ_PARENTHESES, 0); }
		public TerminalNode OPEN_SQ_PARENTHESES() { return getToken(WaccParser.OPEN_SQ_PARENTHESES, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		return arrayType(0);
	}

	private ArrayTypeContext arrayType(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, _parentState);
		ArrayTypeContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_arrayType, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
				{
				setState(201); baseType();
				setState(202); match(OPEN_SQ_PARENTHESES);
				setState(203); match(CLOSE_SQ_PARENTHESES);
				}
				break;
			case PAIR:
				{
				setState(205); pairType();
				setState(206); match(OPEN_SQ_PARENTHESES);
				setState(207); match(CLOSE_SQ_PARENTHESES);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(216);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_arrayType);
					setState(211);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(212); match(OPEN_SQ_PARENTHESES);
					setState(213); match(CLOSE_SQ_PARENTHESES);
					}
					} 
				}
				setState(218);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PairTypeContext extends ParserRuleContext {
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public PairElemTypeContext pairElemType(int i) {
			return getRuleContext(PairElemTypeContext.class,i);
		}
		public List<PairElemTypeContext> pairElemType() {
			return getRuleContexts(PairElemTypeContext.class);
		}
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public PairTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairTypeContext pairType() throws RecognitionException {
		PairTypeContext _localctx = new PairTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219); match(PAIR);
			setState(220); match(OPEN_PARENTHESES);
			setState(221); pairElemType();
			setState(222); match(COMMA);
			setState(223); pairElemType();
			setState(224); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemTypeContext extends ParserRuleContext {
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairElemTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElemType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairElemType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemTypeContext pairElemType() throws RecognitionException {
		PairElemTypeContext _localctx = new PairElemTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pairElemType);
		try {
			setState(229);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(226); baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(227); arrayType(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(228); match(PAIR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CharLiterExprContext extends ExprContext {
		public TerminalNode CHAR_LTR() { return getToken(WaccParser.CHAR_LTR, 0); }
		public CharLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitCharLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentExprContext extends ExprContext {
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public IdentExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIdentExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryOpContext extends ExprContext {
		public TerminalNode NEQ() { return getToken(WaccParser.NEQ, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GTE() { return getToken(WaccParser.GTE, 0); }
		public TerminalNode LT() { return getToken(WaccParser.LT, 0); }
		public TerminalNode GT() { return getToken(WaccParser.GT, 0); }
		public TerminalNode MOD() { return getToken(WaccParser.MOD, 0); }
		public TerminalNode OR() { return getToken(WaccParser.OR, 0); }
		public TerminalNode LTE() { return getToken(WaccParser.LTE, 0); }
		public TerminalNode MUL() { return getToken(WaccParser.MUL, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode AND() { return getToken(WaccParser.AND, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode EQ() { return getToken(WaccParser.EQ, 0); }
		public TerminalNode DIV() { return getToken(WaccParser.DIV, 0); }
		public BinaryOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBinaryOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExprContext extends ExprContext {
		public UnaryOperContext unaryOper() {
			return getRuleContext(UnaryOperContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntLiterExprContext extends ExprContext {
		public IntLiterContext intLiter() {
			return getRuleContext(IntLiterContext.class,0);
		}
		public IntLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIntLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolLiterExprContext extends ExprContext {
		public BoolLiterContext boolLiter() {
			return getRuleContext(BoolLiterContext.class,0);
		}
		public BoolLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBoolLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PairLiterExprContext extends ExprContext {
		public PairLiterContext pairLiter() {
			return getRuleContext(PairLiterContext.class,0);
		}
		public PairLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayElemExprContext extends ExprContext {
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public ArrayElemExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayElemExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprInParenthesesExprContext extends ExprContext {
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ExprInParenthesesExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitExprInParenthesesExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringLiterExprContext extends ExprContext {
		public TerminalNode STRING_LTR() { return getToken(WaccParser.STRING_LTR, 0); }
		public StringLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitStringLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(232); unaryOper();
				setState(233); expr(15);
				}
				break;
			case 2:
				{
				_localctx = new IntLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(235); intLiter();
				}
				break;
			case 3:
				{
				_localctx = new BoolLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236); boolLiter();
				}
				break;
			case 4:
				{
				_localctx = new CharLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(237); match(CHAR_LTR);
				}
				break;
			case 5:
				{
				_localctx = new StringLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(238); match(STRING_LTR);
				}
				break;
			case 6:
				{
				_localctx = new PairLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(239); pairLiter();
				}
				break;
			case 7:
				{
				_localctx = new IdentExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(240); match(IDENT);
				}
				break;
			case 8:
				{
				_localctx = new ArrayElemExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(241); arrayElem();
				}
				break;
			case 9:
				{
				_localctx = new ExprInParenthesesExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(242); match(OPEN_PARENTHESES);
				setState(243); expr(0);
				setState(244); match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(289);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(287);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(248);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(249); match(MUL);
						setState(250); expr(15);
						}
						break;
					case 2:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(251);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(252); match(DIV);
						setState(253); expr(14);
						}
						break;
					case 3:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(254);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(255); match(MOD);
						setState(256); expr(13);
						}
						break;
					case 4:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(257);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(258); match(PLUS);
						setState(259); expr(12);
						}
						break;
					case 5:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(260);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(261); match(MINUS);
						setState(262); expr(11);
						}
						break;
					case 6:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(263);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(264); match(GT);
						setState(265); expr(10);
						}
						break;
					case 7:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(266);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(267); match(GTE);
						setState(268); expr(9);
						}
						break;
					case 8:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(269);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(270); match(LT);
						setState(271); expr(8);
						}
						break;
					case 9:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(272);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(273); match(LTE);
						setState(274); expr(7);
						}
						break;
					case 10:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(275);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(276); match(EQ);
						setState(277); expr(6);
						}
						break;
					case 11:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(278);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(279); match(NEQ);
						setState(280); expr(5);
						}
						break;
					case 12:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(281);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(282); match(AND);
						setState(283); expr(4);
						}
						break;
					case 13:
						{
						_localctx = new BinaryOpContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(284);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(285); match(OR);
						setState(286); expr(3);
						}
						break;
					}
					} 
				}
				setState(291);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IntLiterContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(WaccParser.INTEGER, 0); }
		public IntSignContext intSign() {
			return getRuleContext(IntSignContext.class,0);
		}
		public IntLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIntLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntLiterContext intLiter() throws RecognitionException {
		IntLiterContext _localctx = new IntLiterContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_intLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(292); intSign();
				}
			}

			setState(295); match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntSignContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public IntSignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intSign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitIntSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntSignContext intSign() throws RecognitionException {
		IntSignContext _localctx = new IntSignContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_intSign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(WaccParser.NOT, 0); }
		public TerminalNode ORD() { return getToken(WaccParser.ORD, 0); }
		public TerminalNode LEN() { return getToken(WaccParser.LEN, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode CHR() { return getToken(WaccParser.CHR, 0); }
		public UnaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitUnaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperContext unaryOper() throws RecognitionException {
		UnaryOperContext _localctx = new UnaryOperContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayElemContext extends ParserRuleContext {
		public List<TerminalNode> CLOSE_SQ_PARENTHESES() { return getTokens(WaccParser.CLOSE_SQ_PARENTHESES); }
		public List<TerminalNode> OPEN_SQ_PARENTHESES() { return getTokens(WaccParser.OPEN_SQ_PARENTHESES); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OPEN_SQ_PARENTHESES(int i) {
			return getToken(WaccParser.OPEN_SQ_PARENTHESES, i);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CLOSE_SQ_PARENTHESES(int i) {
			return getToken(WaccParser.CLOSE_SQ_PARENTHESES, i);
		}
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public ArrayElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayElem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayElemContext arrayElem() throws RecognitionException {
		ArrayElemContext _localctx = new ArrayElemContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_arrayElem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(301); match(IDENT);
			setState(306); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(302); match(OPEN_SQ_PARENTHESES);
					setState(303); expr(0);
					setState(304); match(CLOSE_SQ_PARENTHESES);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(308); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolLiterContext extends ParserRuleContext {
		public TerminalNode FALSE() { return getToken(WaccParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(WaccParser.TRUE, 0); }
		public BoolLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitBoolLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolLiterContext boolLiter() throws RecognitionException {
		BoolLiterContext _localctx = new BoolLiterContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_boolLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiterContext extends ParserRuleContext {
		public TerminalNode CLOSE_SQ_PARENTHESES() { return getToken(WaccParser.CLOSE_SQ_PARENTHESES, 0); }
		public TerminalNode OPEN_SQ_PARENTHESES() { return getToken(WaccParser.OPEN_SQ_PARENTHESES, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArrayLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitArrayLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiterContext arrayLiter() throws RecognitionException {
		ArrayLiterContext _localctx = new ArrayLiterContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_arrayLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312); match(OPEN_SQ_PARENTHESES);
			setState(321);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTEGER) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << OPEN_PARENTHESES) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << IDENT) | (1L << STRING_LTR) | (1L << CHAR_LTR))) != 0)) {
				{
				setState(313); expr(0);
				setState(318);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(314); match(COMMA);
					setState(315); expr(0);
					}
					}
					setState(320);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(323); match(CLOSE_SQ_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairLiterContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(WaccParser.NULL, 0); }
		public PairLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccParserVisitor ) return ((WaccParserVisitor<? extends T>)visitor).visitPairLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairLiterContext pairLiter() throws RecognitionException {
		PairLiterContext _localctx = new PairLiterContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_pairLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325); match(NULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return stat_sempred((StatContext)_localctx, predIndex);
		case 11: return arrayType_sempred((ArrayTypeContext)_localctx, predIndex);
		case 14: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean arrayType_sempred(ArrayTypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 14);
		case 3: return precpred(_ctx, 13);
		case 4: return precpred(_ctx, 12);
		case 5: return precpred(_ctx, 11);
		case 6: return precpred(_ctx, 10);
		case 7: return precpred(_ctx, 9);
		case 8: return precpred(_ctx, 8);
		case 9: return precpred(_ctx, 7);
		case 10: return precpred(_ctx, 6);
		case 11: return precpred(_ctx, 5);
		case 12: return precpred(_ctx, 4);
		case 13: return precpred(_ctx, 3);
		case 14: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@\u014a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\7\2\62"+
		"\n\2\f\2\16\2\65\13\2\3\2\3\2\7\29\n\2\f\2\16\2<\13\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\3\3\5\3F\n\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4P\n\4\f\4"+
		"\16\4S\13\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\5\6\u0087\n\6\3\6\3\6\3\6\3\6\3\6\7\6\u008e\n\6\f\6\16\6\u0091\13\6"+
		"\3\6\7\6\u0094\n\6\f\6\16\6\u0097\13\6\3\7\3\7\3\7\5\7\u009c\n\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00ab\n\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\5\b\u00b4\n\b\3\t\3\t\3\t\7\t\u00b9\n\t\f\t\16\t\u00bc"+
		"\13\t\3\n\3\n\3\n\3\n\5\n\u00c2\n\n\3\13\3\13\3\13\5\13\u00c7\n\13\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00d4\n\r\3\r\3\r\3\r\7\r"+
		"\u00d9\n\r\f\r\16\r\u00dc\13\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\5\17\u00e8\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00f9\n\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u0122\n\20\f\20\16\20\u0125\13\20"+
		"\3\21\5\21\u0128\n\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\6\24\u0135\n\24\r\24\16\24\u0136\3\25\3\25\3\26\3\26\3\26\3\26\7"+
		"\26\u013f\n\26\f\26\16\26\u0142\13\26\5\26\u0144\n\26\3\26\3\26\3\27\3"+
		"\27\3\27\2\5\n\30\36\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",\2\6\3\2\63\66\3\2\7\b\3\2\b\f\3\289\u016e\2\63\3\2\2\2\4A\3\2\2\2\6"+
		"L\3\2\2\2\bT\3\2\2\2\n\u0086\3\2\2\2\f\u009b\3\2\2\2\16\u00b3\3\2\2\2"+
		"\20\u00b5\3\2\2\2\22\u00c1\3\2\2\2\24\u00c6\3\2\2\2\26\u00c8\3\2\2\2\30"+
		"\u00d3\3\2\2\2\32\u00dd\3\2\2\2\34\u00e7\3\2\2\2\36\u00f8\3\2\2\2 \u0127"+
		"\3\2\2\2\"\u012b\3\2\2\2$\u012d\3\2\2\2&\u012f\3\2\2\2(\u0138\3\2\2\2"+
		"*\u013a\3\2\2\2,\u0147\3\2\2\2./\7\5\2\2/\60\7\6\2\2\60\62\7\30\2\2\61"+
		".\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\63"+
		"\3\2\2\2\66:\7\35\2\2\679\5\4\3\28\67\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3"+
		"\2\2\2;=\3\2\2\2<:\3\2\2\2=>\5\n\6\2>?\7\37\2\2?@\7\2\2\3@\3\3\2\2\2A"+
		"B\5\24\13\2BC\7;\2\2CE\7\31\2\2DF\5\6\4\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2"+
		"\2GH\7\32\2\2HI\7\36\2\2IJ\5\n\6\2JK\7\37\2\2K\5\3\2\2\2LQ\5\b\5\2MN\7"+
		"\33\2\2NP\5\b\5\2OM\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\7\3\2\2\2S"+
		"Q\3\2\2\2TU\5\24\13\2UV\7;\2\2V\t\3\2\2\2WX\b\6\1\2X\u0087\7 \2\2YZ\5"+
		"\24\13\2Z[\7;\2\2[\\\7\34\2\2\\]\5\16\b\2]\u0087\3\2\2\2^_\5\f\7\2_`\7"+
		"\34\2\2`a\5\16\b\2a\u0087\3\2\2\2bc\7!\2\2c\u0087\5\f\7\2de\7\"\2\2e\u0087"+
		"\5\36\20\2fg\7#\2\2g\u0087\5\36\20\2hi\7$\2\2i\u0087\5\36\20\2jk\7%\2"+
		"\2k\u0087\5\36\20\2lm\7&\2\2m\u0087\5\36\20\2no\7\'\2\2op\5\36\20\2pq"+
		"\7(\2\2qr\5\n\6\2rs\7*\2\2s\u0087\3\2\2\2tu\7\'\2\2uv\5\36\20\2vw\7(\2"+
		"\2wx\5\n\6\2xy\7)\2\2yz\5\n\6\2z{\7*\2\2{\u0087\3\2\2\2|}\7+\2\2}~\5\36"+
		"\20\2~\177\7,\2\2\177\u0080\5\n\6\2\u0080\u0081\7-\2\2\u0081\u0087\3\2"+
		"\2\2\u0082\u0083\7\35\2\2\u0083\u0084\5\n\6\2\u0084\u0085\7\37\2\2\u0085"+
		"\u0087\3\2\2\2\u0086W\3\2\2\2\u0086Y\3\2\2\2\u0086^\3\2\2\2\u0086b\3\2"+
		"\2\2\u0086d\3\2\2\2\u0086f\3\2\2\2\u0086h\3\2\2\2\u0086j\3\2\2\2\u0086"+
		"l\3\2\2\2\u0086n\3\2\2\2\u0086t\3\2\2\2\u0086|\3\2\2\2\u0086\u0082\3\2"+
		"\2\2\u0087\u0095\3\2\2\2\u0088\u0089\f\3\2\2\u0089\u008f\7\30\2\2\u008a"+
		"\u008b\5\n\6\2\u008b\u008c\7\30\2\2\u008c\u008e\3\2\2\2\u008d\u008a\3"+
		"\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090"+
		"\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0094\5\n\6\4\u0093\u0088\3\2"+
		"\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096"+
		"\13\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u009c\7;\2\2\u0099\u009c\5&\24\2"+
		"\u009a\u009c\5\22\n\2\u009b\u0098\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009a"+
		"\3\2\2\2\u009c\r\3\2\2\2\u009d\u00b4\5*\26\2\u009e\u009f\7.\2\2\u009f"+
		"\u00a0\7\31\2\2\u00a0\u00a1\5\36\20\2\u00a1\u00a2\7\33\2\2\u00a2\u00a3"+
		"\5\36\20\2\u00a3\u00a4\7\32\2\2\u00a4\u00b4\3\2\2\2\u00a5\u00b4\5\22\n"+
		"\2\u00a6\u00a7\7/\2\2\u00a7\u00a8\7;\2\2\u00a8\u00aa\7\31\2\2\u00a9\u00ab"+
		"\5\20\t\2\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2"+
		"\u00ac\u00b4\7\32\2\2\u00ad\u00ae\7;\2\2\u00ae\u00af\7\60\2\2\u00af\u00b0"+
		"\7\31\2\2\u00b0\u00b1\7;\2\2\u00b1\u00b4\7\32\2\2\u00b2\u00b4\5\36\20"+
		"\2\u00b3\u009d\3\2\2\2\u00b3\u009e\3\2\2\2\u00b3\u00a5\3\2\2\2\u00b3\u00a6"+
		"\3\2\2\2\u00b3\u00ad\3\2\2\2\u00b3\u00b2\3\2\2\2\u00b4\17\3\2\2\2\u00b5"+
		"\u00ba\5\36\20\2\u00b6\u00b7\7\33\2\2\u00b7\u00b9\5\36\20\2\u00b8\u00b6"+
		"\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"+
		"\21\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00be\7\61\2\2\u00be\u00c2\5\36"+
		"\20\2\u00bf\u00c0\7\62\2\2\u00c0\u00c2\5\36\20\2\u00c1\u00bd\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c2\23\3\2\2\2\u00c3\u00c7\5\26\f\2\u00c4\u00c7\5\30"+
		"\r\2\u00c5\u00c7\5\32\16\2\u00c6\u00c3\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"+
		"\u00c5\3\2\2\2\u00c7\25\3\2\2\2\u00c8\u00c9\t\2\2\2\u00c9\27\3\2\2\2\u00ca"+
		"\u00cb\b\r\1\2\u00cb\u00cc\5\26\f\2\u00cc\u00cd\7<\2\2\u00cd\u00ce\7="+
		"\2\2\u00ce\u00d4\3\2\2\2\u00cf\u00d0\5\32\16\2\u00d0\u00d1\7<\2\2\u00d1"+
		"\u00d2\7=\2\2\u00d2\u00d4\3\2\2\2\u00d3\u00ca\3\2\2\2\u00d3\u00cf\3\2"+
		"\2\2\u00d4\u00da\3\2\2\2\u00d5\u00d6\f\3\2\2\u00d6\u00d7\7<\2\2\u00d7"+
		"\u00d9\7=\2\2\u00d8\u00d5\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2"+
		"\2\2\u00da\u00db\3\2\2\2\u00db\31\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00de"+
		"\7\67\2\2\u00de\u00df\7\31\2\2\u00df\u00e0\5\34\17\2\u00e0\u00e1\7\33"+
		"\2\2\u00e1\u00e2\5\34\17\2\u00e2\u00e3\7\32\2\2\u00e3\33\3\2\2\2\u00e4"+
		"\u00e8\5\26\f\2\u00e5\u00e8\5\30\r\2\u00e6\u00e8\7\67\2\2\u00e7\u00e4"+
		"\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e6\3\2\2\2\u00e8\35\3\2\2\2\u00e9"+
		"\u00ea\b\20\1\2\u00ea\u00eb\5$\23\2\u00eb\u00ec\5\36\20\21\u00ec\u00f9"+
		"\3\2\2\2\u00ed\u00f9\5 \21\2\u00ee\u00f9\5(\25\2\u00ef\u00f9\7?\2\2\u00f0"+
		"\u00f9\7>\2\2\u00f1\u00f9\5,\27\2\u00f2\u00f9\7;\2\2\u00f3\u00f9\5&\24"+
		"\2\u00f4\u00f5\7\31\2\2\u00f5\u00f6\5\36\20\2\u00f6\u00f7\7\32\2\2\u00f7"+
		"\u00f9\3\2\2\2\u00f8\u00e9\3\2\2\2\u00f8\u00ed\3\2\2\2\u00f8\u00ee\3\2"+
		"\2\2\u00f8\u00ef\3\2\2\2\u00f8\u00f0\3\2\2\2\u00f8\u00f1\3\2\2\2\u00f8"+
		"\u00f2\3\2\2\2\u00f8\u00f3\3\2\2\2\u00f8\u00f4\3\2\2\2\u00f9\u0123\3\2"+
		"\2\2\u00fa\u00fb\f\20\2\2\u00fb\u00fc\7\r\2\2\u00fc\u0122\5\36\20\21\u00fd"+
		"\u00fe\f\17\2\2\u00fe\u00ff\7\16\2\2\u00ff\u0122\5\36\20\20\u0100\u0101"+
		"\f\16\2\2\u0101\u0102\7\17\2\2\u0102\u0122\5\36\20\17\u0103\u0104\f\r"+
		"\2\2\u0104\u0105\7\7\2\2\u0105\u0122\5\36\20\16\u0106\u0107\f\f\2\2\u0107"+
		"\u0108\7\b\2\2\u0108\u0122\5\36\20\r\u0109\u010a\f\13\2\2\u010a\u010b"+
		"\7\20\2\2\u010b\u0122\5\36\20\f\u010c\u010d\f\n\2\2\u010d\u010e\7\21\2"+
		"\2\u010e\u0122\5\36\20\13\u010f\u0110\f\t\2\2\u0110\u0111\7\22\2\2\u0111"+
		"\u0122\5\36\20\n\u0112\u0113\f\b\2\2\u0113\u0114\7\23\2\2\u0114\u0122"+
		"\5\36\20\t\u0115\u0116\f\7\2\2\u0116\u0117\7\24\2\2\u0117\u0122\5\36\20"+
		"\b\u0118\u0119\f\6\2\2\u0119\u011a\7\25\2\2\u011a\u0122\5\36\20\7\u011b"+
		"\u011c\f\5\2\2\u011c\u011d\7\26\2\2\u011d\u0122\5\36\20\6\u011e\u011f"+
		"\f\4\2\2\u011f\u0120\7\27\2\2\u0120\u0122\5\36\20\5\u0121\u00fa\3\2\2"+
		"\2\u0121\u00fd\3\2\2\2\u0121\u0100\3\2\2\2\u0121\u0103\3\2\2\2\u0121\u0106"+
		"\3\2\2\2\u0121\u0109\3\2\2\2\u0121\u010c\3\2\2\2\u0121\u010f\3\2\2\2\u0121"+
		"\u0112\3\2\2\2\u0121\u0115\3\2\2\2\u0121\u0118\3\2\2\2\u0121\u011b\3\2"+
		"\2\2\u0121\u011e\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121\3\2\2\2\u0123"+
		"\u0124\3\2\2\2\u0124\37\3\2\2\2\u0125\u0123\3\2\2\2\u0126\u0128\5\"\22"+
		"\2\u0127\u0126\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u012a"+
		"\7\4\2\2\u012a!\3\2\2\2\u012b\u012c\t\3\2\2\u012c#\3\2\2\2\u012d\u012e"+
		"\t\4\2\2\u012e%\3\2\2\2\u012f\u0134\7;\2\2\u0130\u0131\7<\2\2\u0131\u0132"+
		"\5\36\20\2\u0132\u0133\7=\2\2\u0133\u0135\3\2\2\2\u0134\u0130\3\2\2\2"+
		"\u0135\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\'\3"+
		"\2\2\2\u0138\u0139\t\5\2\2\u0139)\3\2\2\2\u013a\u0143\7<\2\2\u013b\u0140"+
		"\5\36\20\2\u013c\u013d\7\33\2\2\u013d\u013f\5\36\20\2\u013e\u013c\3\2"+
		"\2\2\u013f\u0142\3\2\2\2\u0140\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141"+
		"\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0143\u013b\3\2\2\2\u0143\u0144\3\2"+
		"\2\2\u0144\u0145\3\2\2\2\u0145\u0146\7=\2\2\u0146+\3\2\2\2\u0147\u0148"+
		"\7:\2\2\u0148-\3\2\2\2\31\63:EQ\u0086\u008f\u0095\u009b\u00aa\u00b3\u00ba"+
		"\u00c1\u00c6\u00d3\u00da\u00e7\u00f8\u0121\u0123\u0127\u0136\u0140\u0143";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}