package wacc.slack.generators;

public class ControlFlowLabelGenerator {
	private static int num = 0;
	
	public static String getNewUniqueLabel() {
		num++;
		return "cf_" + num;
	}

}
