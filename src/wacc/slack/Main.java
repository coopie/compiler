package wacc.slack;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) throws Exception {
		String inputFile = null;
		String outputFile = null;
		Compiler compiler = new Compiler();
		int optimisationLevel = 0;
		
		if (args.length > 0) {
			inputFile = args[0];

			if (!args[0].endsWith(".wacc")) {
				throw new RuntimeException(
						"File to compile must end with \".wacc\"");
			}

			outputFile = args[0].substring(0, args[0].length() - 5) + ".s";
			outputFile = outputFile.replaceAll(".*/", "");
			if(args.length > 1 && args[1].startsWith("-O")) {
				optimisationLevel = Integer.parseInt(args[1].substring(2));
			}
		}

		InputStream is = System.in;

		if (inputFile != null) {
			is = new FileInputStream(inputFile);
		}

		PrintStream out = new PrintStream(new File(outputFile));

		out.print(compiler.compile(is,optimisationLevel));

		out.print('\n');
		out.close();

		System.exit(0);
	}
	
}
