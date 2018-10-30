# analise_lexica
Trabalho apresentado a disciplina de Compiladores. Projeto de desenvolvimento de um analisador lexico de uma linguagem hipotetica.

Retorno do compilador para o arquivo teste arquivo.txt.

Classe Main eh a que instancia o Lexer.

START < start >
INT  < int >
ID < a >
EQUAL < = >
INTEGER < 0 >
SEMICOLON < ; >
DO < do >
ID < a >
EQUAL < = >
ID < a >
SUM < + >
INTEGER < 1 >
SEMICOLON < ; >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Valor de a :  >
SUM < + >
ID < a >
CLOSE_PAR < ) >
SEMICOLON < ; >
WHILE  < while >
OPEN_PAR < ( >
ID < a >
LESS_EQUAL < <= >
INTEGER < 10 >
CLOSE_PAR < ) >
END  < end >
INT  < int >
ID < b >
SEMICOLON < ; >
DO < do >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Entre com um valor para b : >
CLOSE_PAR < ) >
SEMICOLON < ; >
SCAN  < scan >
OPEN_PAR < ( >
ID < b >
CLOSE_PAR < ) >
SEMICOLON < ; >
WHILE  < while >
OPEN_PAR < ( >
ID < b >
DIFF < <> >
INTEGER < 0 >
CLOSE_PAR < ) >
END  < end >
INT  < int >
ID < resultado >
EQUAL < = >
ID < a >
SUM < + >
ID < b >
SEMICOLON < ; >
INT  < int >
ID < c >
SEMICOLON < ; >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Qual o valor de a + b : >
CLOSE_PAR < ) >
SEMICOLON < ; >
SCAN  < scan >
OPEN_PAR < ( >
ID < c >
CLOSE_PAR < ) >
SEMICOLON < ; >
IF  < if >
ID < c >
COMPARATION < == >
ID < resultado >
THEN  < then >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Voce aceitou! >
CLOSE_PAR < ) >
SEMICOLON < ; >
ELSE < else >
IF  < if >
ID < c >
LESS_THAN < < >
ID < resultado >
THEN  < then >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Voce errou para baixo >
CLOSE_PAR < ) >
SEMICOLON < ; >
ELSE < else >
PRINT < print >
OPEN_PAR < ( >
LITERAL < Voce errou para cima >
CLOSE_PAR < ) >
SEMICOLON < ; >
END  < end >
END  < end >
EXIT  < exit >
