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
  SKIP                          #skipStat
| type IDENT ASSIGN assignRhs   #assignStat
| assignLhs ASSIGN assignRhs    #assignStat
| READ assignLhs                #readStat
| FREE expr                     #freeStat
| RETURN expr                   #returnStat
| EXIT expr                     #exitStat
| PRINT expr                    #printStat
| PRINTLN expr                  #printlnStat
| IF expr THEN stat FI          #ifWithoutElseStat
| IF expr THEN stat ELSE stat FI#ifStat
| WHILE expr DO  stat  DONE     #whileStat
| BEGIN stat  END               #beginStat
| stat SEMICOLON (stat SEMICOLON)* stat       #statList;

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
  intLiter	           #intLiterExpr
| boolLiter		       #boolLiterExpr
| CHAR_LTR		       #charLiterExpr
| STRING_LTR		   #stringLiterExpr
| pairLiter			   #pairLiterExpr
| IDENT				   #identExpr
| arrayElem			   #arrayElemExpr
| unaryOper expr	   #unaryExpr
| expr MUL expr 	   #binaryOp
| expr DIV expr        #binaryOp
| expr MOD expr        #binaryOp
| expr PLUS expr       #binaryOp
| expr MINUS expr      #binaryOp
| expr GT expr         #binaryOp
| expr GTE expr        #binaryOp
| expr LT expr         #binaryOp
| expr LTE expr        #binaryOp
| expr EQ expr         #binaryOp
| expr NEQ expr        #binaryOp
| expr AND expr        #binaryOp
| expr OR expr         #binaryOp
| OPEN_PARENTHESES expr CLOSE_PARENTHESES  #exprInParenthesesExpr
;

intLiter : intSign? INTEGER ;

intSign : PLUS | MINUS ;

unaryOper : NOT | MINUS | LEN | ORD | CHR ;

arrayElem : IDENT (OPEN_SQ_PARENTHESES expr CLOSE_SQ_PARENTHESES)+ ;

boolLiter : TRUE | FALSE ;

arrayLiter : OPEN_SQ_PARENTHESES ( expr (COMMA expr)* )? CLOSE_SQ_PARENTHESES ;

pairLiter : NULL ;

