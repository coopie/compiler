parser grammar WaccParser;

// NEED TO SORT THIS CRAP OUT WHEN WE FIGURE OUT HOW TO
// BASICALLY WE NEED TO GET RID OF WHITESPACE SOMETIMES...
// michael stop fooling around its fine
// WHITESPACE SHOULD BE IGNORED WHEN NOT EXPECTED

options {
  tokenVocab=WaccLexer;
}

// EOF indicates that the program must consume to the end of the input.
program: BEGIN func* stat END EOF;

func : type WS+ IDENT OPEN_PARENTHESES paramList? CLOSE_PARENTHESES IS WS+ stat WS+ END ;

paramList : param (COMMA param)* ;

param : type WS+ IDENT ;

stat : 
  SKIP 
| type WS+ IDENT ASSIGN assignRhs
| assignLhs ASSIGN assignRhs
| READ WS+ assignLhs
| FREE WS+ expr
| RETURN WS+ expr
| EXIT WS+ expr
| PRINT WS+ expr
| PRINTLN WS+ expr
| IF WS+ expr WS+ THEN WS+ stat WS+ ELSE WS+ stat WS+ FI
| WHILE WS+ expr WS+ DO WS+ stat WS+ DONE
| BEGIN WS+ stat WS+ END
| stat SEMICOLON stat ;

assignLhs :
  IDENT
| arrayElem
| pairElem ;

assignRhs :
  expr
| arrayLiter
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
| pairElem
| CALL WS+ IDENT OPEN_PARENTHESES argList? CLOSE_PARENTHESES ;

argList : expr (COMMA expr)* ;

pairElem : 
  FST WS+ expr
| SND WS+ expr ;

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
| charLiter
| strLiter
| pairLiter
| IDENT
| arrayElem
| unaryOper expr
| expr binaryOper expr
| OPEN_PARENTHESES expr CLOSE_PARENTHESES ;

unaryOper : NOT | MINUS | LEN | ORD | CHR ;

binaryOper : MUL | DIV | MOD | PLUS | MINUS | GT | GTE | LT | LTE | EQ | NEQ | AND | OR ;

arrayElem : IDENT OPEN_SQ_PARENTHESES expr CLOSE_SQ_PARENTHESES ;

intLiter : intSign? INTEGER ;

intSign : PLUS | MINUS ;

boolLiter : TRUE | FALSE ;

charLiter : SINGLE_QUOTE character SINGLE_QUOTE ;

strLiter : DOUBLE_QUOTE character* DOUBLE_QUOTE ;

character : ASCIICHAR | ESCAPE_CHAR ESCAPED_CHARS ;

arrayLiter : OPEN_SQ_PARENTHESES ( expr (COMMA expr)* )? CLOSE_SQ_PARENTHESES ;

pairLiter : NULL ;

