lexer grammar WaccLexer;

COMMENT :'#' .*? '\n' -> skip;

//operators
PLUS : '+' ;
MINUS : '-' ;
NOT : '!' ;
LEN : 'len' ;
ORD : 'ord' ;
CHR : 'chr' ;
MUL : '*' ;
DIV : '/' ;
MOD : '%' ;
GT : '>' ;
GTE : '>=' ;
LT : '<' ;
LTE : '<=' ;
EQ : '==' ;
NEQ : '!=' ;
AND : '&&' ;
OR : '||' ;

//needs improvement


SEMICOLON : ';' ;

//brackets
OPEN_PARENTHESES : '(' ;
CLOSE_PARENTHESES : ')' ;
COMMA : ',' ;
ASSIGN : '=' ;

//numbers
fragment DIGIT : '0'..'9' ; 

INTEGER : DIGIT+ ;

//prog
BEGIN : 'begin' ;
IS : 'is' ;
END : 'end' ;

//stats
SKIP : 'skip' ;
READ : 'read' ;
FREE : 'free' ;
RETURN : 'return' ;
EXIT : 'exit' ;
PRINT : 'print' ;
PRINTLN : 'println' ;
IF : 'if' ;
THEN : 'then' ;
ELSE : 'else' ;
FI : 'fi' ;
WHILE : 'while' ;
DO : 'do' ;
DONE : 'done' ;




//assign rhs
NEWPAIR : 'newpair' ;
CALL : 'call' ;

//pair elem
FST : 'fst' ;
SND : 'snd' ;

//base types
INT : 'int' ;
BOOL : 'bool' ;
CHAR : 'char' ;
STRING : 'string' ;
PAIR : 'pair' ;

//bool literal
TRUE : 'true' ;
FALSE : 'false' ;

IDENT : [_a-zA-Z][_a-zA-Z0-9]* ;//(‘_’|‘a’-‘z’|‘A’-‘Z’)(‘_’|‘a’-‘z’|‘A’-‘Z’|‘0’-‘9’)* ;

NULL : 'null';

//square brackets
OPEN_SQ_PARENTHESES : '[' ;
CLOSE_SQ_PARENTHESES : ']' ;

STRING_LTR: '"' .*? '"';
CHAR_LTR: '\'' .*? '\'';


WS : [ \n\t\r] -> skip ;
