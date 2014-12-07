package wacc.slack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import wacc.slack.AST.WaccAST;
import wacc.slack.AST.visitors.IntermediateCodeGenerator;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.controlFlow.ControlFlowGraph;
import wacc.slack.instructions.AssemblerDirective;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.ComplexRegisterAllocator;
import wacc.slack.instructions.visitors.TemporaryReplacer;
import wacc.slack.interferenceGraph.InterferenceGraph;
import wacc.slack.interferenceGraph.InterferenceGraphColourer;
import wacc.slack.interferenceGraph.RegisterMapping;

public class GenerateOptimizedIntermediateCode implements
		Callable<Deque<PseudoInstruction>> {

	private static final int NUMBER_OF_INSTRUCTIONS_BEFEFORE_CONSTANT_POOL_CLOSE = 350;
	private ComplexRegisterAllocator registerAllocator = new ComplexRegisterAllocator();
	private final WaccAST ast;
	private final int optimisationLevel;

	public GenerateOptimizedIntermediateCode(WaccAST ast, int optimisationLevel) {
		this.ast = ast;
		this.optimisationLevel = optimisationLevel;

	}

	@Override
	public Deque<PseudoInstruction> call() throws Exception {
		IntermediateCodeGenerator visitor = new IntermediateCodeGenerator();

		Deque<PseudoInstruction> intermediateCode = ast.accept(visitor);
		intermediateCode = doOptimisations(intermediateCode, optimisationLevel);
		return intermediateCode;
	}

	private Deque<PseudoInstruction> doOptimisations(
			Deque<PseudoInstruction> intermediateCode, int optimizationLevel) {
		if (optimizationLevel == 0) {
			return simpleRegisterAllocation(intermediateCode);
		}

		ControlFlowGraph cfg = new ControlFlowGraph(intermediateCode);
		InterferenceGraph ig = new InterferenceGraph(cfg);
		InterferenceGraphColourer igc = new InterferenceGraphColourer(ig);
		RegisterMapping mapping = igc.generateTemporaryRegisterMappings();
		registerAllocator = new ComplexRegisterAllocator(mapping);
		// igc.generateTemporaryRegisterMappings(mapping);

		

	
		return simpleRegisterAllocation(intermediateCode);
	}

	private Deque<PseudoInstruction> simpleRegisterAllocation(
			Deque<PseudoInstruction> intermediateCode) {
		Deque<PseudoInstruction> finalCode = new ArrayDeque<PseudoInstruction>();
		
		int numInstruction = 0;
		for (PseudoInstruction ps : intermediateCode) {
			finalCode.addAll(ps.accept(registerAllocator));
			finalCode.add(new AssemblerDirective("\n")); // for debugging
															// purposes
			numInstruction++;
			//splits the constant pool and shit
			if(numInstruction % NUMBER_OF_INSTRUCTIONS_BEFEFORE_CONSTANT_POOL_CLOSE == 0) {
				finalCode.add(new AssemblerDirective(".ltorg"));
			}
		}
		return finalCode;
	}

	public Set<ArmRegister> getRegistersUsed() {
		return registerAllocator.getArmRegistersUsed();
	}
	
	public Set<TemporaryRegister> getSpilledRegisters() {
		return registerAllocator.getSpilledRegistersUsed();
	}
}
