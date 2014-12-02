package wacc.slack.interferenceGraph;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ColouredGraphChecker {
	
	public static Matcher<InterferenceGraph> coloured() {
		return new BaseMatcher<InterferenceGraph>() {
			
			private InterferenceGraph ig = null;
			
			@Override
			public boolean matches(Object item) {
				if(!(item instanceof InterferenceGraph)) return false;
				ig = (InterferenceGraph)item;
				
				for(InterferenceGraphNode n : ig) {
					if (!n.isColoured()) return false;
					
					int nodeColour = n.getColour();
					
					for(InterferenceGraphNode neighbour : ig.getAdjecent(n)) {
						if(neighbour.getColour() == nodeColour) {
							return false;
						}
					}
				}
				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("graph is not coloured correctly:\n" + ig.toString());
			}
		};

			 
	}

}
