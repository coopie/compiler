package wacc.slack.errorHandling;

import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;

import wacc.slack.FilePosition;
import wacc.slack.errorHandling.errorRecords.ErrorRecords;
import wacc.slack.errorHandling.errorRecords.SyntaxError;

public class WaccSyntaxtErrorListner implements ANTLRErrorListener {

	ErrorRecords records = ErrorRecords.getInstance();
	
	@Override
	public void reportAmbiguity(@NotNull Parser arg0, @NotNull DFA arg1,
			int arg2, int arg3, boolean arg4, @Nullable BitSet arg5,
			@NotNull ATNConfigSet arg6) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reportAttemptingFullContext(@NotNull Parser arg0,
			@NotNull DFA arg1, int arg2, int arg3, @Nullable BitSet arg4,
			@NotNull ATNConfigSet arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reportContextSensitivity(@NotNull Parser arg0,
			@NotNull DFA arg1, int arg2, int arg3, int arg4,
			@NotNull ATNConfigSet arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void syntaxError(@NotNull Recognizer<?, ?> parser,
			@Nullable Object offendingToken, int line, int charPos, @NotNull String defaultMessage,
			@Nullable RecognitionException arg5) {
		
		records.record(new SyntaxError(defaultMessage, new FilePosition(line,charPos)));
		

	}

}
