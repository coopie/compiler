package wacc.slack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.SimpleRegisterAllocator;
import wacc.slack.instructions.visitors.TemporaryReplacer;
import wacc.slack.interferenceGraph.InterferenceGraph;
import wacc.slack.interferenceGraph.InterferenceGraphColourer;

public class GenerateOptimizedIntermediateCode implements Callable<Deque<PseudoInstruction>> {

	private final WaccAST ast;
	private final int optimisationLevel;
	private int regsUsed = 0;

	public GenerateOptimizedIntermediateCode(WaccAST ast, int optimisationLevel) {
		this.ast = ast;
		this.optimisationLevel = optimisationLevel;
		
	}
	
	@Override
	public Deque<PseudoInstruction> call() throws Exception {
		IntermediateCodeGenerator visitor = new IntermediateCodeGenerator();
		
		Deque<PseudoInstruction> intermediateCode = ast.accept(visitor);
		//intermediateCode = doOptimisations(intermediateCode, optimisationLevel);
		regsUsed = Integer.parseInt(visitor.getTemporaryRegisterGenerator().getValue());
		return intermediateCode;
	}
	
	private Deque<PseudoInstruction> doOptimisations(
			Deque<PseudoInstruction> intermediateCode, int optimizationLevel) {
		if (optimizationLevel == 0) {
			return simpleRegisterAllocation(intermediateCode);
		}

		Deque<PseudoInstruction> codeWithoutTemporaries = new LinkedList<>();
		final Map<Register, ArmRegister> mapping = new HashMap<>();
		ControlFlowGraph cfg = new ControlFlowGraph(intermediateCode);
		InterferenceGraph ig = new InterferenceGraph(cfg);
		InterferenceGraphColourer igc = new InterferenceGraphColourer(ig);
		// igc.generateTemporaryRegisterMappings(mapping);

		for (PseudoInstruction i : intermediateCode) {
			codeWithoutTemporaries.addAll(i.accept(new TemporaryReplacer(
					mapping)));
		}
		
		//TODO: probably wrong
		regsUsed = cfg.getAllRegs().size();
		
		return codeWithoutTemporaries;
	}

	private Deque<PseudoInstruction> simpleRegisterAllocation(
			Deque<PseudoInstruction> intermediateCode) {
		Deque<PseudoInstruction> finalCode = new ArrayDeque<PseudoInstruction>();
		for (PseudoInstruction ps : intermediateCode) {
			finalCode.addAll(ps.accept(new SimpleRegisterAllocator()));
			finalCode.add(new AssemblerDirective("\n")); // for debugging
															// purposes
		}
		return finalCode;
	}

	public int getNumberOfRegsUsed() {
		return regsUsed;
	}

}
