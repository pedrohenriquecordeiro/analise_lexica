/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import java.io.IOException;
import lex.Lexer;
import lex.Tag;
import lex.Token;

/**
 *
 * @author pedro 30/10/18
 */
public class Syntaxer {

    final Tag tag;
    Token token;
    Lexer lexer;

    public Syntaxer(String filename) throws IOException {
        this.tag = new Tag();
        this.lexer = new Lexer(filename);
        this.token = this.lexer.scan();
    }

    void advance() throws IOException {
        this.token = this.lexer.scan();
    }

    void eat(String token_lexeme) throws IOException {
        if (token.tag.equals(this.token.lexeme)) {
            advance();
        } else {
            error(token_lexeme, this.token.lexeme);
        }
    }

    private void error(String token_expected, String token_gived) {
        System.out.println("Gived    : " + token_gived);
        System.out.println("Expected : " + token_expected);
    }

    // ---------------------- productions -----------------------------------------------
    //program ::= start [decl-list] stmt-list exit
    void program() throws IOException {
        if (this.token.lexeme.equals(Tag.START)) {
            eat(Tag.START);
            if (this.token.lexeme.equals(Tag.INT)
                    || this.token.lexeme.equals(Tag.FLOAT)
                    || this.token.lexeme.equals(Tag.STRING)) {
                decl_list();
            }
            stmt_list();
            eat(Tag.END);
        } else {
            error(Tag.START, this.token.lexeme);
        }
    }

    //decl-list ::= decl {decl}
    void decl_list() throws IOException {

        do {
            decl();
        } while (this.token.lexeme.equals(Tag.INT)
                || this.token.lexeme.equals(Tag.FLOAT)
                || this.token.lexeme.equals(Tag.STRING));

    }

    //decl ::= type ident-list ";"
    void decl() throws IOException {
        type();
        ident_list();
        eat(Tag.SEMICOLON);
    }

    //ident-list ::= identifier {"," identifier}
    void ident_list() throws IOException {
        identifier();
        while (this.token.lexeme.equals(Tag.COMMA)) {
            eat(Tag.COMMA);
            identifier();
        }
    }

    //type ::= int | float | string
    void type() throws IOException {
        if (this.token.lexeme.equals(Tag.INT)) {
            eat(Tag.INT);
        } else if (this.token.lexeme.equals(Tag.FLOAT)) {
            eat(Tag.FLOAT);
        } else if (this.token.lexeme.equals(Tag.STRING)) {
            eat(Tag.STRING);
        } else {
            error(Tag.INT, this.token.lexeme);
        }
    }

    //stmt-list ::= stmt {stmt}
    void stmt_list() throws IOException {
        do {
            stmt();
        } while (this.token.lexeme.equals(Tag.IF)
                || this.token.lexeme.equals(Tag.ID)
                || this.token.lexeme.equals(Tag.WHILE)
                || this.token.lexeme.equals(Tag.SCAN)
                || this.token.lexeme.equals(Tag.PRINT));
    }

    //stmt ::= assign-stmt ";"  | if-stmt | while-stmt| read-stmt ";" | write-stmt ";"
    void stmt() throws IOException {
        if (this.token.lexeme.equals(Tag.ID)) {
            assign_stmt();
            eat(Tag.SEMICOLON);
        } else if (this.token.lexeme.equals(Tag.IF)) {
            if_stmt();
        } else if (this.token.lexeme.equals(Tag.DO)) {
            while_stmt();
        } else if (this.token.lexeme.equals(Tag.SCAN)) {
            read_stmt();
            eat(Tag.SEMICOLON);
        } else if (this.token.lexeme.equals(Tag.PRINT)) {
            write_stmt();
            eat(Tag.SEMICOLON);
        } else {
            error(Tag.ID, this.token.lexeme);
        }
    }

    //assign-stmt ::= identifier "=" simple_expr
    void assign_stmt() throws IOException {
        eat(Tag.ID);
        eat(Tag.EQUAL);
        simple_expr();
    }

    //if-stmt ::= if condition then stmt-list if-stmt-2
    void if_stmt() throws IOException {
        eat(Tag.IF);
        condition();
        eat(Tag.THEN);
        stmt_list();
        if_stmt_2();
    }

    //if-stmt-2 ::= end | else stmt-list end
    void if_stmt_2() throws IOException {
        if (this.token.lexeme.equals(Tag.END)) {
            eat(Tag.END);
        } else if (this.token.lexeme.equals(Tag.ELSE)) {
            eat(Tag.ELSE);
            stmt_list();
            eat(Tag.END);
        }
    }

    //condition ::= expression
    void condition() throws IOException {
        expression();
    }

    //while-stmt ::= do stmt-list stmt-sufix
    void while_stmt() throws IOException {
        eat(Tag.DO);
        stmt_list();
        stmt_sufix();
    }

    //stmt-sufix ::= while condition end
    void stmt_sufix() throws IOException {
        eat(Tag.WHILE);
        condition();
        eat(Tag.END);
    }

    //read-stmt ::= scan "(" identifier ")"
    
    
    //write-stmt ::= print "(" writable ")"
    
    
    //writable ::= simple-expr | literal
    
    
    //expression ::= simple-expr expression-2
    void expression() throws IOException {
        simple_expr();
        expression2();
    }

    //expression-2 ::= relop simple-expr | L
    void expression2() throws IOException {
        if (this.token.lexeme.equals(Tag.DIFF)
                || this.token.lexeme.equals(Tag.LESS_THAN)
                || this.token.lexeme.equals(Tag.LESS_EQUAL)
                || this.token.lexeme.equals(Tag.GREATHER_THAN)
                || this.token.lexeme.equals(Tag.GREATHER_EQUAL)) {
            relop();
            simple_expr();
        }

    }

    //simple-expr ::= term simple-expr-2
    void simple_expr() throws IOException {
        term();
        simple_expr_2();
    }

    //simple-expr-2 ::= addop term simple-expr-2 | L
    
    
    //term ::= factor-a term-2
    void term() throws IOException {
        factor_a();
        term_2();
    }

    //term-2 ::= mulop factor-a term-2 | L
    
    
    //fator-a ::= factor | not factor | "-" factor
    void factor_a() throws IOException {
        if (this.token.lexeme.equals(Tag.ID)
                || this.token.lexeme.equals(Tag.FLOATING)
                || this.token.lexeme.equals(Tag.INTEGER)
                || this.token.lexeme.equals(Tag.LITERAL)
                || this.token.lexeme.equals(Tag.OPEN_PAR)) {
            factor();
        } else if (this.token.lexeme.equals(Tag.NOT)) {
            eat(Tag.NOT);
            factor();
        } else if (this.token.lexeme.equals(Tag.MINUS)) {
            eat(Tag.MINUS);
            factor();
        }
    }

    //factor ::= identifier | constant | "(" expression ")"
    void factor() throws IOException {
        if (this.token.lexeme.equals(Tag.ID)) {
            eat(Tag.ID);
        } else if (this.token.lexeme.equals(Tag.FLOATING)
                || this.token.lexeme.equals(Tag.INTEGER)
                || this.token.lexeme.equals(Tag.LITERAL)) {
            constant();
        } else if (this.token.lexeme.equals(Tag.OPEN_PAR)) {
            eat(Tag.OPEN_PAR);
            expression();
            eat(Tag.CLOSE_PAR);
        }
    }

    //relop ::= "==" | ">" | ">=" | "<" | "<=" | "<>"
    void relop() throws IOException {
        if (this.token.lexeme.equals(Tag.COMPARATION)) {
            eat(Tag.COMPARATION);
        } else if (this.token.lexeme.equals(Tag.GREATHER_EQUAL)) {
            eat(Tag.GREATHER_EQUAL);
        } else if (this.token.lexeme.equals(Tag.GREATHER_THAN)) {
            eat(Tag.GREATHER_THAN);
        } else if (this.token.lexeme.equals(Tag.LESS_EQUAL)) {
            eat(Tag.LESS_EQUAL);
        } else if (this.token.lexeme.equals(Tag.LESS_THAN)) {
            eat(Tag.LESS_THAN);
        } else if (this.token.lexeme.equals(Tag.DIFF)) {
            eat(Tag.DIFF);
        } else {
            error("relop", this.token.lexeme);
        }

    }

    //addop ::= "+" | "-" | or
    
    
    //mulop ::= "*" | "/" | and
    
    
    //constant ::= integer_const | float_const | literal
    void constant() throws IOException {
        if (this.token.lexeme.equals(Tag.FLOATING)) {
            eat(Tag.FLOATING);
        } else if (this.token.lexeme.equals(Tag.INTEGER)) {
            eat(Tag.INTEGER);
        } else if (this.token.lexeme.equals(Tag.LITERAL)) {
            eat(Tag.LITERAL);
        } else {
            error("CONSTANT", this.token.lexeme);
        }
    }

    //integer_const ::= digit {digit}
    void integer_const() throws IOException {
        if (this.token.lexeme.equals(Tag.INTEGER)) {
            eat(Tag.INTEGER);
        } else {
            error(Tag.INTEGER, this.token.lexeme);
        }
    }

    //float_const ::= digit{digit} “.”digit{digit}
    void float_const() throws IOException {
        if (this.token.lexeme.equals(Tag.FLOATING)) {
            eat(Tag.FLOATING);
        } else {
            error(Tag.FLOATING, this.token.lexeme);
        }
    }

    //literal ::= " “" {caractere} "”"
    void literal() throws IOException {
        if (this.token.lexeme.equals(Tag.LITERAL)) {
            eat(Tag.LITERAL);
        } else {
            error(Tag.LITERAL, this.token.lexeme);
        }
    }

    //identifier ::= letter {letter | digit }
    void identifier() throws IOException {
        if (this.token.lexeme.equals(Tag.ID)) {
            eat(Tag.ID);
        } else {
            error(Tag.ID, this.token.lexeme);
        }
    }

}
