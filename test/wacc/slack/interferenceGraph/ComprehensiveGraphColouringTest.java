	package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wacc.slack.interferenceGraph.ColouredGraphChecker.coloured;

import java.util.Deque;

import org.junit.After;
import org.junit.Test;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.Expr.BinaryExprAST;
import wacc.slack.AST.Expr.UnaryExprAST;
import wacc.slack.AST.Expr.ValueExprAST;
import wacc.slack.AST.literals.BinaryOp;
import wacc.slack.AST.literals.IntLiter;
import wacc.slack.AST.literals.UnaryOp;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.PseudoInstruction;

public class ComprehensiveGraphColouringTest {
	
	private InterferenceGraph ig;
	
	@After
	public void cleanGraph() {
		// just to be safe
		ig.clean();
	}
	
	@Test
	public void binaryExprTest() {
		makeInterferenceGraph(new BinaryExprAST(BinaryOp.PLUS,
				new ValueExprAST(new IntLiter(2, null), null) ,
				new ValueExprAST(new IntLiter(2, null), null),
				null));
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		colourer.colour(5, 0);
		assertThat(ig, is(coloured()));
	}
	
	@Test
	public void unaryExprTest() {
		makeInterferenceGraph(new UnaryExprAST(UnaryOp.CHR,
				new ValueExprAST(new IntLiter(2, null), null) ,
				
				null));
		InterferenceGraphColourer colourer = new InterferenceGraphColourer(ig);
		colourer.colour(5, 0);
		assertThat(ig, is(coloured()));
	}
	
	private InterferenceGraph makeInterferenceGraph(WaccAST ast) {
		
		Deque<PseudoInstruction> instructions = ast.accept(new IntermediateCodeGenerator());
		
		ControlFlowGraph cfg = new ControlFlowGraph(instructions);
		ig = new InterferenceGraph(cfg);
		return ig;
	}
	
	
}
