package wacc.slack.errorHandling.expectations;

import wacc.slack.AST.symbolTable.IdentInfo;
import wacc.slack.AST.symbolTable.SymbolTable;
import wacc.slack.errorHandling.errorRecords.ErrorObject;


public interface WaccExpectation extends ErrorObject{
    boolean check();
    void setScope(SymbolTable<IdentInfo> scope);
	String getIdent();
}
