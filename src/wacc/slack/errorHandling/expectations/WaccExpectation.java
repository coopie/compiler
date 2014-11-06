package wacc.slack.errorHandling.expectations;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;


public interface WaccExpectation {
    boolean check();
    void setScope(SymbolTable<IdentInfo> scope);
	String getIdent();
}
