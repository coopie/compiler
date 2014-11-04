import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

import wacc.slack.AST.symbolTable.SymbolTable;

public class SymbolTableTest {

	private SymbolTable<String> table = new SymbolTable<String>();
	
	@Test
	public void canAddAndRetriveStuffToSymbolTable() {
		table.insert("x","hoho");
		
		assertEquals("hoho",table.lookup("x"));
	}
	
	@Test
	public void canAccessOldIdentifiersInANewScope() {
		table.insert("x","hoho");
		
		table = table.initializeNewScope();
		
		assertEquals("hoho",table.lookup("x"));
	}
	
	@Test
	public void cannotAccessForgottenIdentifiers() {
		table = table.initializeNewScope();
		table.insert("x","hoho");
		table = table.popScope();
		assertThat(table.lookup("x"),nullValue());
	}
	
	@Test
	public void cannotPopTopScope() {	
		try {
			table = table.popScope();
		} catch (IllegalStateException e) {
			assertThat(e.getMessage(),is("trying to pop top scope"));
		}
		
	}
}
