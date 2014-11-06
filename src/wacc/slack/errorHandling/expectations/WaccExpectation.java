package wacc.slack.errorHandling.expectations;

public interface WaccExpectation<T> {
	boolean check(T x);
	String getIdent();
}
