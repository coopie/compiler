package wacc.slack.controlFlow;

import java.util.LinkedList;
import java.util.List;

import wacc.slack.assemblyOperands.Register;
import wacc.slack.instructions.PseudoInstruction;
import wacc.slack.instructions.visitors.GenerateAssembly;
import wacc.slack.instructions.visitors.GenerateAssemblyBuilder;
import wacc.slack.instructions.visitors.GetDefinedRegisters;
import wacc.slack.instructions.visitors.GetUsedRegisters;
//TDD class
public class CFGNode {
	// following the structure from the lecture slides for registerAllocation
	
	private final PseudoInstruction ps;

	final List<Register> defs;// = new LinkedList<>();
	final List<Register> uses;// = new LinkedList<>();

	private static GenerateAssembly print = new GenerateAssemblyBuilder().make();

	private final int id;
	
	CFGNode(int id,PseudoInstruction ps) {
		this.id = id;
		this.ps = ps;
		defs = ps.accept(new GetDefinedRegisters());
		uses = ps.accept(new GetUsedRegisters());
	}


	public PseudoInstruction getInstruction() {
		return ps;
	}

	public Iterable<Register> getDefinitions() {
		return defs;
	}

	public Iterable<Register> getUses() {
		return uses;
	}
	
	@Override
	public String toString() {
		return ps.accept(print).replaceAll("\\s+", " ") + " " + uses +  " " + defs;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof CFGNode) {
			return ps.equals(((CFGNode) o).ps) && id == ((CFGNode) o).id;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return ps.hashCode()*6047 +  id*7823;
	}
	
}
