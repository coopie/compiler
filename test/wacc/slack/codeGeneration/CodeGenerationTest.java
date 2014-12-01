package wacc.slack.codeGeneration;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import wacc.slack.Compiler;

public class CodeGenerationTest {
	
	Compiler compiler = new Compiler();
	
	protected void programTestAssert(String in, String expectedOut) {
		InputStream stream = new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8));
		String s = null;
		try {
			s = compiler.compile(stream);
		} catch (Exception e) {
			// should not happen as only valid programs should be tested
		}
		
		assertEquals(expectedOut, s);
	}

}
