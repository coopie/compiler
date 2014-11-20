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
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.ControlFlowLabelGenerator;
import wacc.slack.generators.LiteralLabelGenerator;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.And;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.BLInstruction;
import wacc.slack.instructions.BranchInstruction;
import wacc.slack.instructions.Condition;
import wacc.slack.instructions.Label;
import wacc.slack.instructions.Ldr;
import wacc.slack.instructions.Mov;
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

	private final class DefaultOperandVisitor implements OperandVisitor<Operand> {

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
	private Operand returnedOperand = null;
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
		
		// d = doCFG(d) // replaces all the temporarz registers with real ones and do the optimization
		
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
		// TODO Auto-generated method stub
		return null;
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
		// must work out branch instruction here (new method or switch statement?)
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
		Deque<PseudoInstruction> instr = 	printInstructionGenerator(expr.accept(this),returnedOperand,expr.getType());
		instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(NEW_LINE_CHAR)));
		instr.addLast(new BLInstruction("printf"));
		return instr;
	}

	@Override
	public Deque<PseudoInstruction> visit(PrintStatementAST printStat) {
		ExprAST expr = printStat.getExpr();
		return printInstructionGenerator(expr.accept(this),returnedOperand,expr.getType());
	}

	private Deque<PseudoInstruction> printInstructionGenerator(Deque<PseudoInstruction> instr, Operand retOp, Type t) {	
		if(t.equals(BaseType.T_int)) {
			instr.add(new Mov(ArmRegister.r1, retOp.accept(new DefaultOperandVisitor())));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(INT_FORMAT_LABEL)));
		} else if(t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.add(new Ldr(ArmRegister.r1, retOp.accept(new DefaultOperandVisitor())));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(STRING_FORMAT_LABEL)));
		} else if (t.equals(new WaccArrayType(BaseType.T_char))) {
			instr.add(new Ldr(ArmRegister.r1, retOp.accept(new DefaultOperandVisitor())));
			instr.addLast(new Ldr(ArmRegister.r0, new ImmediateValue(CHAR_FORMAT_LABEL)));
		} else if (t.equals(BaseType.T_bool)) {
			instr.add(new Ldr(ArmRegister.r0, retOp.accept(new DefaultOperandVisitor())));
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

//		/* syscall exit(int status) */
//	    mov     %r0, $0     /* status -> 0 */
//	    mov     %r7, $1     /* exit is syscall #1 */
//	    swi     $0          /* invoke syscall */

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
		Operand exprRegL = returnedOperand;
		
		instrList.addAll(binExpr.getExprR().accept(this));
		Operand exprRegR = returnedOperand;
		
		Operand trL = trg.generate();
		Operand trR = trg.generate();

		// ldr tr exprRegL
		instrList.add(new Ldr(trL, exprRegL));
		
		// ldr tr exprRegR
		instrList.add(new Ldr(trR, exprRegR));
		
		Operand destReg = trg.generate();

		// TODO: Add instructions for each binary op
		switch (binExpr.getBinaryOp()) {
		case MUL:
			
		case DIV:
			
		case MOD:
		
		case PLUS:
			
		case MINUS:

		case GT:
			
		case GTE:
			
		case LT:
			
		case LTE:
			
		case EQ:
			
		case NEQ:
			
		case AND:
			
		case OR:
			
		}
		
		returnedOperand = destReg;
		return instrList;
	}

	@Override
	public Deque<PseudoInstruction> visit(UnaryExprAST unExpr) {
		Deque<PseudoInstruction> instrList = new LinkedList<PseudoInstruction>();
		
		instrList.addAll(unExpr.getExpr().accept(this));
		Operand tr = trg.generate();
		
		// ldr tr returnedOperand
		instrList.add(new Ldr(tr, returnedOperand));
		
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
		Label literalLabel = new Label(LiteralLabelGenerator.getNewUniqueLabel());
		
		// Literal is added to the .data section
		textSection.add(literalLabel);
		
		// If it is a string literal
		if(valueExpr.getType().equals(new WaccArrayType(BaseType.T_char))){
			textSection.add(new AssemblerDirective(".ascii \"" + valueExpr.getValue() + "\\0\""));
		} else if (valueExpr.getType().equals(BaseType.T_int)) {
			returnedOperand = new ImmediateValue(Integer.parseInt(valueExpr.getValue()));
			return new LinkedList<PseudoInstruction>();
		} else if (valueExpr.getType().equals(BaseType.T_char)) {
			textSection.add(new AssemblerDirective(".byte '" + valueExpr.getValue() + "'"));
		} else if (valueExpr.getType().equals(BaseType.T_bool)) {
			if (valueExpr.getValue().equals("true")) {
				returnedOperand = TRUE_LABEL;
			} else {
				returnedOperand = FALSE_LABEL;
			}
			return new LinkedList<PseudoInstruction>();
		}
		
		// Return the label of the literal
		returnedOperand = literalLabel;
		return new LinkedList<PseudoInstruction>();
	}

	@Override
	public Deque<PseudoInstruction> visit(VariableAST variable) {
		// TODO Auto-generated method stub
		return null;
	}
}
