package wacc.slack.AST.visitors;

import java.util.Deque;
import java.util.LinkedList;

import wacc.slack.AST.ProgramAST;
import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.ExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.assignables.ArrayElemAST;
import wacc.slack.AST.assignables.CallAST;
import wacc.slack.AST.assignables.FstAST;
import wacc.slack.AST.assignables.FuncAST;
import wacc.slack.AST.assignables.NewPairAST;
import wacc.slack.AST.assignables.SndAST;
import wacc.slack.AST.assignables.VariableAST;
import wacc.slack.AST.literals.ArrayLiterAST;
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
import wacc.slack.AST.types.BaseType;
import wacc.slack.AST.types.Type;
import wacc.slack.AST.types.WaccArrayType;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.Operand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.ControlFlowLabelGenerator;
import wacc.slack.generators.LiteralLabelGenerator;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Add;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Cmp;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
import wacc.slack.instructions.Orr;
import wacc.slack.instructions.Pop;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.Push;
import wacc.slack.instructions.Sub;
import wacc.slack.instructions.Swi;

// NB: use LinkedList 

public class IntermediateCodeGenerator implements
		ASTVisitor<Deque<PseudoInstruction>> {

	private static final String STRING_FORMAT_LABEL = "string_format";
	private static final String CHAR_FORMAT_LABEL = "char_format";
	private static final String NEW_LINE_CHAR = "new_line_char";
	private static final String INT_FORMAT_LABEL = "int_format";
	private static final Label TRUE_LABEL = new Label("l_true");
	private static final Label FALSE_LABEL = new Label("l_false");

	private final class DefaultOperandVisitor implements
			OperandVisitor<Operand> {

		@Override
		public Operand visit(ArmRegister realRegister) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(TemporaryRegister temporaryRegister) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Operand visit(Label label) {
			return new ImmediateValue(label.getName());
		}

		@Override
		public Operand visit(ImmediateValue immediateValue) {
			return immediateValue;
		}

	}

	private Deque<PseudoInstruction> textSection = new LinkedList<>();
	private Register returnedOperand = null;
	private TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();

	@Override
	public Deque<PseudoInstruction> visit(FuncAST func) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(ProgramAST prog) {

		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		Deque<PseudoInstruction> d = prog.getStatements().accept(this);

		// d = doCFG(d) // replaces all the temporarz registers with real ones
		// and do the optimization

		instrList.add(new AssemblerDirective(".global main"));
		// TODO: functions, exit codes maybe

		// --- implementation of the "main" function, i.e. the stats after the
		// function definitions

		instrList.add(new Label("main"));

		instrList.add(new Push(ArmRegister.lr));

		instrList.addAll(d);

		instrList.add(new Pop(ArmRegister.pc));

		instrList.add(new AssemblerDirective(".text"));
		initTextSection();
		instrList.addAll(textSection);

		return instrList;
	}

	private void initTextSection() {
		textSection.add(new Label(NEW_LINE_CHAR));
		textSection.add(new AssemblerDirective(".ascii \"\\n\\0\""));

		textSection.add(new Label(STRING_FORMAT_LABEL));
		textSection.add(new AssemblerDirective(".ascii \"%s\\0\""));

		textSection.add(new Label(CHAR_FORMAT_LABEL));
		textSection.add(new AssemblerDirective(".ascii \"%c\\0\""));

		textSection.add(new Label(INT_FORMAT_LABEL));
		textSection.add(new AssemblerDirective(".ascii \"%d\\0\""));

		textSection.add(new Label(TRUE_LABEL.getName()));
		textSection.add(new AssemblerDirective(".ascii \"true\\0\""));

		textSection.add(new Label(FALSE_LABEL.getName()));
		textSection.add(new AssemblerDirective(".ascii \"false\\0\""));

	}

	@Override
	public Deque<PseudoInstruction> visit(StatListAST statAST) {

		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		for (WaccAST stat : statAST.getChildren()) {
			instrList.addAll(stat.accept(this));
		}
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(AssignStatAST assignStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(BeginEndAST beginEnd) {
		return new LinkedList<PseudoInstruction>();
	}

	@Override
	public Deque<PseudoInstruction> visit(IfStatementAST ifStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		Label falsel = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		Label endl = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		instrList.addAll(ifStat.getCond().accept(this));
		instrList.add(new Cmp(returnedOperand, new ImmediateValue("0")));
		instrList.add(new BranchInstruction(Condition.EQ, falsel));
		instrList.addAll(ifStat.getTrueStats().accept(this));
		instrList.add(new BranchInstruction(Condition.AL, endl));
		instrList.add(falsel);
		instrList.addAll(ifStat.getFalseStats().accept(this));
		instrList.add(endl);
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(SkipStatementAST skipStat) {
		return new LinkedList<PseudoInstruction>();
	}

	@Override
	public Deque<PseudoInstruction> visit(WhileStatementAST whileStat) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		Label start = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		Label end = new Label(ControlFlowLabelGenerator.getNewUniqueLabel());
		instrList.add(new BranchInstruction(Condition.AL, end));
		instrList.add(start);
		instrList.addAll(whileStat.getBody().accept(this));
		instrList.add(end);
		instrList.addAll(whileStat.getCond().accept(this));
		instrList.add(new Cmp(returnedOperand, new ImmediateValue("1")));
		instrList.add(new BranchInstruction(Condition.EQ, start));
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReturnStatementAST exprStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(PrintlnStatementAST printlnStat) {

		ExprAST expr = printlnStat.getExpr();
		Deque<PseudoInstruction> instr = printInstructionGenerator(
				expr.accept(this), returnedOperand, expr.getType());
		instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(NEW_LINE_CHAR)));
		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(PrintStatementAST printStat) {
		ExprAST expr = printStat.getExpr();
		return printInstructionGenerator(expr.accept(this), returnedOperand,
				expr.getType());
	}

	private Deque<PseudoInstruction> printInstructionGenerator(
			Deque<PseudoInstruction> instr, Register retReg, Type t) {

		instr.addLast(new Mov(ArmRegister.r1, retReg));
		if (t.equals(BaseType.T_int)) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					INT_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					STRING_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(
					CHAR_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_bool)) {
			// instr.addLast(new Mov(ArmRegister.r0, retReg));
		}

		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(ReadStatementAST readStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(ExitStatementAST exitStat) {

		// /* syscall exit(int status) */
		// mov %r0, $0 /* status -> 0 */
		// mov %r7, $1 /* exit is syscall #1 */
		// swi $0 /* invoke syscall */

		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		// TODO: fill in the null here for the operand visitor of the expression
		instrList.addAll(exitStat.getExpr().accept(this));

		instrList.add(new Mov(ArmRegister.r0, returnedOperand));

		instrList.add(new Mov(ArmRegister.r7, new ImmediateValue(0)));

		instrList.add(new Swi());

		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(FreeStatementAST freeStat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(ArrayElemAST arrayElem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(FstAST fst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(SndAST snd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(ArrayLiterAST arrayLiter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(CallAST call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(NewPairAST newPair) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deque<PseudoInstruction> visit(BinaryExprAST binExpr) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(binExpr.getExprL().accept(this));
		Register exprRegL = returnedOperand;

		instrList.addAll(binExpr.getExprR().accept(this));
		Register exprRegR = returnedOperand;

		/*
		 * Register trL = trg.generate(); Register trR = trg.generate();
		 * 
		 * // TODO: Much overhead, such bad -- check that the Ldr instruction is
		 * the best way to do it // ldr trL exprRegL instrList.add(new Ldr(trL,
		 * exprRegL));
		 * 
		 * // ldr trR exprRegR instrList.add(new Ldr(trR, exprRegR));
		 */

		Register destReg = trg.generate();

		// TODO: Add instructions for each binary op
		switch (binExpr.getBinaryOp()) {
		case MUL:
			// add destReg trL trR
			instrList.add(new Add(destReg, exprRegL, exprRegR));

		case DIV:

		case MOD:

		case PLUS:
			// add destReg trL trR
			instrList.add(new Add(destReg, exprRegL, exprRegR));

		case MINUS:
			// sub destReg trL trR
			instrList.add(new Sub(destReg, exprRegL, exprRegR));

		case GT:
			// cmp trL trR
			// movgt destReg, #1
			// movle destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.GT));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.LE));

		case GTE:
			// cmp trL trR
			// movge destReg, #1
			// movlt destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.GE));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.LT));

		case LT:
			// cmp trL trR
			// movlt destReg, #1
			// movge destReg, #0
			instrList.add(new Cmp(exprRegR, exprRegL));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.LT));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.GE));

		case LTE:
			// cmp trL trR
			// movle destReg, #1
			// movgt destReg, #0
			instrList.add(new Cmp(exprRegR, exprRegL));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.LE));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.GT));

		case EQ:
			// cmp trL trR
			// moveq destReg, #1
			// movne destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.EQ));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.NE));

		case NEQ:
			// cmp trL trR
			// movne destReg, #1
			// moveq destReg, #0
			instrList.add(new Cmp(exprRegL, exprRegR));
			instrList.add(new Mov(destReg, new ImmediateValue(1), Condition.NE));
			instrList.add(new Mov(destReg, new ImmediateValue(0), Condition.EQ));
			
		case AND:
			// and destReg trL trR
			instrList.add(new And(destReg, exprRegL, exprRegR));

		case OR:
			// and destReg trL trR
			instrList.add(new Orr(destReg, exprRegL, exprRegR));
		}

		returnedOperand = destReg;
		return instrList;
	}
	
	// TODO
	private void checkDivideByZero() {
		
	}

	@Override
	public Deque<PseudoInstruction> visit(UnaryExprAST unExpr) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();

		instrList.addAll(unExpr.getExpr().accept(this));

		/*
		 * Register tr = trg.generate();
		 * 
		 * // ldr tr returnedOperand instrList.add(new Ldr(tr,
		 * returnedOperand));
		 */

		Register tr = returnedOperand;

		// TODO: Add instructions for each unary op
		switch (unExpr.getUnaryOp()) {
		case NOT:
			// and tr tr 0
			instrList.add(new And(tr, tr, new ImmediateValue(0)));

		case MINUS:
			// sub tr 0 tr
			instrList.add(new Sub(tr, new ImmediateValue(0), tr));

		case LEN:

		case ORD:

		case CHR:

		}

		returnedOperand = tr;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(ValueExprAST valueExpr) {
		Label literalLabel = new Label(
				LiteralLabelGenerator.getNewUniqueLabel());
		Register ret = trg.generate();
		Deque<PseudoInstruction> instr = new LinkedList<PseudoInstruction>();

		// Literal is added to the .data section
		textSection.add(literalLabel);

		// If it is a string literal
		if (valueExpr.getType().equals(new WaccArrayType(BaseType.T_char))) {
			textSection.add(new AssemblerDirective(".ascii \""
					+ valueExpr.getValue() + "\\0\""));
			instr.add(new Ldr(ret, new ImmediateValue(literalLabel.getName())));
		} else if (valueExpr.getType().equals(BaseType.T_int)) {
			instr.add(new Mov(ret, new ImmediateValue(Integer
					.parseInt(valueExpr.getValue()))));
		} else if (valueExpr.getType().equals(BaseType.T_char)) {
			textSection.add(new AssemblerDirective(".byte '"
					+ valueExpr.getValue() + "'"));
			instr.add(new Ldr(ret, new ImmediateValue(literalLabel.getName())));
		} else if (valueExpr.getType().equals(BaseType.T_bool)) {
			if (valueExpr.getValue().equals("true")) {
				instr.add(new Ldr(ret, new ImmediateValue(TRUE_LABEL.getName())));
				;
			} else {
				instr.add(new Ldr(ret,
						new ImmediateValue(FALSE_LABEL.getName())));
				;
			}
		}

		returnedOperand = ret;
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(VariableAST variable) {
		// TODO Auto-generated method stub
		return null;
	}
}
