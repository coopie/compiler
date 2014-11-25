package wacc.slack.interferenceGraph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;

import wacc.slack.assemblyOperands.ArmRegister;

public class InterferenceGraphNodeTest {

	InterferenceGraphNode ign = new InterferenceGraphNode(ArmRegister.r0, 1);
	
	@Test
	public void canGetColour() {
		
	}
	
	@Test
	public void canBeColoured() {
		ign.colour(0);
		assertThat(ign.getColour(), is(0));
	}
	
	@Test
	public void colourInitiallySetToMinusOne() {
		assertThat(ign.getColour(), is(-1));
	}
	
	@Test
	public void isColouredIsFalseWhenNotColoured() {
		assertThat(ign.isColoured(), is(false));
	}
	
	@Test
	public void isColouredIsTrueWhenColoured() {
		ign.colour(0);
		assertThat(ign.isColoured(), is(true));
	}
	
	@Test
	public void hasWeight() {
		assertThat(ign.getWeight(), is(notNullValue()));
	}

}
