package wacc.slack.errorHandling.errorRecords;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import wacc.slack.errorHandling.WaccSyntaxtErrorListner;
import antlr.WaccLexer;
import antlr.WaccParser;


// class for making antlr parser/lexer crunch all of the waccExamples folder
// we are now using labts for this
public class MacroANTLRTest {

	//TODO - make sure that all the different files have separate errorrecord
	
	ErrorRecords ers = ErrorRecords.getInstance(true);
	
	@Test
	public void noErrorsShouldHappenWhenParsingValidFolder() {
		testAntlrRecursively("examples/valid");
		assertThat(ers.isErrorFree(), is(true));
	}
	
	// uses antlr to parse every file found in the directory recursively
	public void testAntlrRecursively(String path) {
		File startingPath = new File(path);
		recursivelyAntlrifyAllFilesInFolder(startingPath);
	}
	
	public void recursivelyAntlrifyAllFilesInFolder(final File startingPath) {
		for (final File fileEntry : startingPath.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	recursivelyAntlrifyAllFilesInFolder(fileEntry);
	        } else {
	        	if(fileEntry.getAbsolutePath().matches("\\.wacc")) {
	        		antlrify(new ByteArrayInputStream(fileEntry.toString().getBytes()));
	        	}
	        }
	    }
	}

	public void antlrify(ByteArrayInputStream bais) {
		try {
			ANTLRInputStream input = new ANTLRInputStream(bais);
			WaccLexer lexer = new WaccLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			WaccParser parser = new WaccParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(new WaccSyntaxtErrorListner());
			ParseTree tree = parser.program();
		} catch (IOException e) {
			// this really shouldn't happen
			e.printStackTrace();
		}
	}

}
