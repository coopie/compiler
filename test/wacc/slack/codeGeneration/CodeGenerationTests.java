package wacc.slack.codeGeneration;

import org.junit.Test;

public class CodeGenerationTests extends CodeGenerationTest {
	
	@Test
	public void simpleSkipTest() {
		// currently fails, just an example of how it is used
		programTestAssert("begin skip end", "");
	}
	
}
