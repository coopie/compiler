package wacc.slack.interferenceGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import wacc.slack.assemblyOperands.Address;
import wacc.slack.assemblyOperands.ArmRegister;
import wacc.slack.assemblyOperands.ImmediateValue;
import wacc.slack.assemblyOperands.NoOperand;
import wacc.slack.assemblyOperands.OperandVisitor;
import wacc.slack.assemblyOperands.Register;
import wacc.slack.assemblyOperands.TemporaryRegister;
import wacc.slack.generators.TemporaryRegisterGenerator;
import wacc.slack.instructions.Label;

public class InterferenceGraphColourer {

	private final int MAX_REGS = 13;

	private InterferenceGraph ig;

	public InterferenceGraphColourer(InterferenceGraph ig) {
		this.ig = ig;
	}

	/**
	 * @param k
	 *            - the number of colours we can colour the graph with
	 * @param numScratchpads
	 *            - number of spill/scratchpad registers
	 * @return - the success of colouring the graph with this configuration
	 */
	public boolean colour(int k, int numScratchpads) {

		// colour all of the real ArmRegisters
		// NB : the colours that we use for the armRegisters will have to
		// correspond to the colour of assigning real registers
		// to temporaries
		int currentColour = 1;
		for (InterferenceGraphNode armNode : findArmRegisterNodes()) {
			armNode.colour(currentColour);
			currentColour++;
		}

		// try to colour all of the constrained nodes, not too worried about the
		// order of this
		for (InterferenceGraphNode constrainedNode : findConstrainedNodes(k)) {
			if (!constrainedNode.isColoured()) {
				tryToColour(constrainedNode, k);
			}
		}

		// deal with unconstrained nodes in weight
		List<InterferenceGraphNode> sortedByWeight = getSortedListOfNodesByWeight();
		for (InterferenceGraphNode n : sortedByWeight) {
			if (!n.isColoured()) {
				tryToColour(n, k);
			}
		}
		// now all the nodes should be coloured. To be certain that we have a
		// nice way of dealing
		// with the scratchpad
		boolean weHaveSpilledNodes = findLargestColour() > k;
		if (numScratchpads == 3) {
			return true;
		} else {
			return !weHaveSpilledNodes;
		}
	}

	private void tryToColour(InterferenceGraphNode n, int k) {
		// TODO: merge the two for loops when confident this is what we want
		Set<InterferenceGraphNode> neighbours = ig.getAdjecent(n);

		// try to colour the node with colours within the range of k
		for (int c = 1; c <= k; c++) {
			boolean neighboursContainThisColour = false;

			// check if any of the neighbours have this colour 
			for (InterferenceGraphNode neighbour : neighbours) {
				if (neighbour.getColour() == c) {
					neighboursContainThisColour = true;
					break;
				}
			}

			if (!neighboursContainThisColour) {
				// if we get here then none of the neighbours have this colour
				n.colour(c);
				return;
			}
		}

		// if we get here, then no colours (1 -> k) are free for the node,
		// then try to colour node with a number bigger than k
		for (int c = k + 1;; k++) {
			boolean neighboursContainThisColour = false;

			for (InterferenceGraphNode neighbour : neighbours) {
				if (neighbour.getColour() == c) {
					neighboursContainThisColour = true;
					break;
				}
			}

			if (!neighboursContainThisColour) {
				// if we get here then none of the neighbours have this colour
				n.colour(c);
				return;
			}
		}
	}

	private Set<InterferenceGraphNode> findConstrainedNodes(int k) {
		Set<InterferenceGraphNode> constrainedNodes = new HashSet<>();

		for (InterferenceGraphNode n : ig) {
			if (ig.isConstrained(n, k)) {
				constrainedNodes.add(n);
			}
		}
		return constrainedNodes;
	}

	private Set<InterferenceGraphNode> findArmRegisterNodes() {
		Set<InterferenceGraphNode> armRegisterNodes = new HashSet<>();

		for (InterferenceGraphNode n : ig) {
			if (isArmRegister(n)) {
				armRegisterNodes.add(n);
			}
		}
		return armRegisterNodes;
	}

	private Boolean isArmRegister(InterferenceGraphNode n) {
		return n.getRegister().accept(new OperandVisitor<Boolean>() {

			@Override
			public Boolean visit(ArmRegister realRegister) {
				return true;
			}

			@Override
			public Boolean visit(TemporaryRegister temporaryRegister) {
				return false;
			}

			@Override
			public Boolean visit(Label label) {
				return false;
			}

				@Override
				public Boolean visit(Address address) {
					return address.getRegister().accept(this);
				}

				@Override
				public Boolean visit(NoOperand noOperand) {
					return false;
				}
			@Override
			public Boolean visit(ImmediateValue immediateValue) {
				return false;
			}
			
			});
	}

	// can be used if we are looking for some optimisation
	private int findLargestColour() {
		int largestColourSoFar = -1;
		for (InterferenceGraphNode n : ig) {
			if (n.getColour() > largestColourSoFar) {
				largestColourSoFar = n.getColour();
			}
		}
		return largestColourSoFar;
	}

	private List<InterferenceGraphNode> getSortedListOfNodesByWeight() {
		List<InterferenceGraphNode> weightList = new LinkedList<>();

		for (InterferenceGraphNode n : ig) {
			weightList.add(n);
		}
		Collections.sort(weightList, new Comparator<InterferenceGraphNode>() {
			@Override
			public int compare(InterferenceGraphNode o1,
					InterferenceGraphNode o2) {
				// we want descending order
				return (-1) * o1.compareTo(o2);
			}
		});
		return weightList;
	}
	
	public TemporaryRegisterMapping generateTemporaryRegisterMappings() {
		return generateTemporaryRegisterMappings(MAX_REGS);
	}

	public TemporaryRegisterMapping generateTemporaryRegisterMappings(int k) {

		// TODO: error with test with colour 0, k still seems to be one
		
		// try to colour the graph with no scratchpad registers
		if (!colour(k, 0)) {
			// then colour the graph allowing nodes to spill into memory,
			// accommodating this
			// with 3 scratchpad registers
			ig.clean();
			k -= 3;
			colour(k, 3);
		}
//		System.out.println(ig);
		
		Map<Integer, ArmRegister> key = new HashMap<Integer, ArmRegister>();
		List<ArmRegister> armRegsSeen = new LinkedList<ArmRegister>();
		
		// find all the armNodes and use them as a basis for starting the mapping
		for (InterferenceGraphNode armNode : findArmRegisterNodes()) {
			key.put(armNode.getColour(), (ArmRegister)armNode.getRegister());
			armRegsSeen.add((ArmRegister)armNode.getRegister());
		}
		//generate colour mappings for other ArmRegisters
		
		for (ArmRegister r : ArmRegister.values()) {
			if(!key.containsValue(r)) {
				for (int i = 1; i <= k; i ++) {
					if(!key.containsKey(i)) {
						key.put(i, r);
						break;
					}
				}
			}
		}
		
		System.out.println("key is : " + key);
		
		Map<TemporaryRegister, ArmRegister> temporaryMappings = new HashMap<TemporaryRegister, ArmRegister>();
		
		// get all the unspilled nodes
		for (InterferenceGraphNode n : getUnspilledTemporaryNodes(k)) {
			// use the key to find the armRegister corresponding to the colour
			temporaryMappings.put((TemporaryRegister)n.getRegister(),
					key.get(n.getColour()));
		}
		
		// do arrange the spilled nodes to timotej's specifications
		
		TemporaryRegisterGenerator trg = new TemporaryRegisterGenerator();
		Map<TemporaryRegister, TemporaryRegister> spilledNodesMap =
							new HashMap<TemporaryRegister, TemporaryRegister>();
		
		for(InterferenceGraphNode n : getSpilledTemporaryNodes(k)) {
			spilledNodesMap.put((TemporaryRegister)n.getRegister(),
					trg.generate(n.getWeight()));
		}
		
		return new TemporaryRegisterMapping(temporaryMappings, spilledNodesMap);
	}
	
	private List<InterferenceGraphNode> getSpilledTemporaryNodes(int k) {
		List<InterferenceGraphNode> spilledNodes = new LinkedList<InterferenceGraphNode>();
		for (InterferenceGraphNode n : ig) {
			if (n.getColour() > k && !isArmRegister(n)) {
				spilledNodes.add(n);
			}
		}
		return spilledNodes;
	}

	private List<InterferenceGraphNode> getUnspilledTemporaryNodes(int k) {
		List<InterferenceGraphNode> unspilledNodes = new LinkedList<InterferenceGraphNode>();
		for (InterferenceGraphNode n : ig) {
			if (n.getColour() <= k && !isArmRegister(n)) {
				unspilledNodes.add(n);
			}
		}
		return unspilledNodes;
	}

	@Override
	public String toString() {
		return ig.toString();
	}

}
