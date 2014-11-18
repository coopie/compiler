package wacc.slack.generators;

public class LiteralLabelGenerator {
	private static int num = 0;
	
	public static String getNewUniqueLabel() {
		num++;
		return "msg_" +  num;
	}
}
