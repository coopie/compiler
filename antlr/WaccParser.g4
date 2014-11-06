parser grammar WaccParser;

//./grun antlr.Wacc program -whatever


options {
  tokenVocab=WaccLexer;
}

// EOF indicates that the program must consume to the end of the input.
program: BEGIN func* stat END EOF;

func : type IDENT OPEN_PARENTHESES paramList? CLOSE_PARENTHESES IS  stat  END ;

paramList : param (COMMA param)* ;

param : type IDENT ;

stat : 
  SKIP 
| type IDENT ASSIGN assignRhs
| assignLhs ASSIGN assignRhs
| READ assignLhs 
| FREE expr
| RETURN expr
| EXIT expr
| PRINT expr
| PRINTLN expr
| IF expr THEN stat ELSE stat FI
| WHILE expr DO  stat  DONE
| BEGIN stat  END
| stat SEMICOLON (stat SEMICOLON)* stat? ;


assignLhs :
  IDENT
| arrayElem
| pairElem ;

assignRhs :
  arrayLiter
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
| pairElem
| CALL IDENT OPEN_PARENTHESES argList? CLOSE_PARENTHESES
| expr
 ;

argList : expr (COMMA expr)* ;

pairElem : 
  FST expr
| SND expr ;

type :
  baseType
| arrayType
| pairType ;

baseType :
  INT
| BOOL 
| CHAR
| STRING ;

arrayType : baseType OPEN_SQ_PARENTHESES CLOSE_SQ_PARENTHESES
| pairType OPEN_SQ_PARENTHESES CLOSE_SQ_PARENTHESES 
| arrayType OPEN_SQ_PARENTHESES CLOSE_SQ_PARENTHESES ;

pairType : PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES ;

pairElemType :
  baseType
| arrayType
| PAIR ;

expr : 
  intLiter	
| boolLiter		
| CHAR_LTR		
| STRING_LTR		
| pairLiter			
| IDENT				
| arrayElem			
| unaryOper expr	
| expr MUL expr 	  
| expr DIV expr  
| expr MOD expr   
| expr PLUS expr   
| expr MINUS expr   
| expr GT expr   
| expr GTE expr   
| expr LT expr   
| expr LTE expr  
| expr EQ expr  
| expr NEQ expr  
| expr AND expr  
| expr OR expr   
| OPEN_PARENTHESES expr CLOSE_PARENTHESES
;

unaryOper : NOT | MINUS | LEN | ORD | CHR ;

arrayElem : IDENT OPEN_SQ_PARENTHESES expr CLOSE_SQ_PARENTHESES ;

intLiter : intSign? INTEGER ;

intSign : PLUS | MINUS ;

boolLiter : TRUE | FALSE ;

arrayLiter : OPEN_SQ_PARENTHESES ( expr (COMMA expr)* ) CLOSE_SQ_PARENTHESES ;

pairLiter : NULL ;

