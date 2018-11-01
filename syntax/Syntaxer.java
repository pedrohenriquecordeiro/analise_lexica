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
    
    public void begin() throws IOException{
        program();
    }

    void advance() throws IOException {
        this.token = this.lexer.scan();
        System.out.println(this.token + "::" + this.lexer.getLine());
    }

    void eat(String tag) throws IOException {
        if (this.token.getTag().equals(tag)) {
            advance();
        } else {
            error(tag, this.token.getTag());
        }
    }

    private void error(String token_expected, String token_gived) {
        System.out.println("ERROR - [line " + this.lexer.getLine() + "] :: < Expected:" + token_expected + " | Gived:" + token_gived + " >");
    }

    // ---------------------- productions -----------------------------------------------
    //program ::= start [decl-list] stmt-list exit
    void program() throws IOException {
        if (this.token.getTag().equals(Tag.START)) {
            eat(Tag.START);
            if (this.token.getTag().equals(Tag.INT)
                    || this.token.getTag().equals(Tag.FLOAT)
                    || this.token.getTag().equals(Tag.STRING)) {
                decl_list();
            }
            stmt_list();
            eat(Tag.EXIT);
        } else {
            error(Tag.START, this.token.getTag());
        }
    }

    //decl-list ::= decl {decl}
    void decl_list() throws IOException {

        do {
            decl();
        } while (this.token.getTag().equals(Tag.INT)
                || this.token.getTag().equals(Tag.FLOAT)
                || this.token.getTag().equals(Tag.STRING));

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
        while (this.token.getTag().equals(Tag.COMMA)) {
            eat(Tag.COMMA);
            identifier();
        }
    }

    //type ::= int | float | string
    void type() throws IOException {
        if (this.token.getTag().equals(Tag.INT)) {
            eat(Tag.INT);
        } else if (this.token.getTag().equals(Tag.FLOAT)) {
            eat(Tag.FLOAT);
        } else if (this.token.getTag().equals(Tag.STRING)) {
            eat(Tag.STRING);
        } else {
            error(Tag.INT, this.token.getTag());
        }
    }

    //stmt-list ::= stmt {stmt}
    void stmt_list() throws IOException {
        do {
            stmt();
        } while (this.token.getTag().equals(Tag.IF)
                || this.token.getTag().equals(Tag.ID)
                || this.token.getTag().equals(Tag.WHILE)
                || this.token.getTag().equals(Tag.SCAN)
                || this.token.getTag().equals(Tag.PRINT));
    }

    //stmt ::= assign-stmt ";"  | if-stmt | while-stmt| read-stmt ";" | write-stmt ";"
    void stmt() throws IOException {
        if (this.token.getTag().equals(Tag.ID)) {
            assign_stmt();
            eat(Tag.SEMICOLON);
        } else if (this.token.getTag().equals(Tag.IF)) {
            if_stmt();
        } else if (this.token.getTag().equals(Tag.DO)) {
            while_stmt();
        } else if (this.token.getTag().equals(Tag.SCAN)) {
            read_stmt();
            eat(Tag.SEMICOLON);
        } else if (this.token.getTag().equals(Tag.PRINT)) {
            write_stmt();
            eat(Tag.SEMICOLON);
        } 
    }

    //assign-stmt ::= identifier "=" simple_expr
    void assign_stmt() throws IOException {
        identifier();
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
        if (this.token.getTag().equals(Tag.END)) {
            eat(Tag.END);
        } else if (this.token.getTag().equals(Tag.ELSE)) {
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
    void read_stmt() throws IOException {
        eat(Tag.SCAN);
        eat(Tag.OPEN_PAR);
        identifier();
        eat(Tag.CLOSE_PAR);
    }

    //write-stmt ::= print "(" writable ")"
    void write_stmt() throws IOException {
        eat(Tag.PRINT);
        eat(Tag.OPEN_PAR);
        writable();
        eat(Tag.CLOSE_PAR);
    }

    //writable ::= simple-expr | literal
    void writable() throws IOException {
        if (this.token.getTag().equals(Tag.ID)
                || this.token.getTag().equals(Tag.FLOATING)
                || this.token.getTag().equals(Tag.INTEGER)
                || this.token.getTag().equals(Tag.OPEN_PAR)
                || this.token.getTag().equals(Tag.NOT)
                || this.token.getTag().equals(Tag.MINUS)) {
            simple_expr();
        } else if (this.token.getTag().equals(Tag.LITERAL)) {
            literal();
        }
    }

    //expression ::= simple-expr expression-2
    void expression() throws IOException {
        simple_expr();
        expression2();
    }

    //expression-2 ::= relop simple-expr | L
    void expression2() throws IOException {
        if (this.token.getTag().equals(Tag.DIFF)
                || this.token.getTag().equals(Tag.LESS_THAN)
                || this.token.getTag().equals(Tag.LESS_EQUAL)
                || this.token.getTag().equals(Tag.GREATHER_THAN)
                || this.token.getTag().equals(Tag.GREATHER_EQUAL)
                || this.token.getTag().equals(Tag.COMPARATION)) {
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
    void simple_expr_2() throws IOException {
        if (this.token.getTag().equals(Tag.SUM)
                || this.token.getTag().equals(Tag.MINUS)
                || this.token.getTag().equals(Tag.OR)) {
            addop();
            term();
            simple_expr_2();
        }

    }

    //term ::= factor-a term-2
    void term() throws IOException {
        factor_a();
        term_2();
    }

    //term-2 ::= mulop factor-a term-2 | L
    void term_2() throws IOException {
        if (this.token.getTag().equals(Tag.MULT)
                || this.token.getTag().equals(Tag.DIV)
                || this.token.getTag().equals(Tag.AND)) {
            mulop();
            factor_a();
            term_2();
        }
    }

    //fator-a ::= factor | not factor | "-" factor
    void factor_a() throws IOException {
        if (this.token.getTag().equals(Tag.ID)
                || this.token.getTag().equals(Tag.FLOATING)
                || this.token.getTag().equals(Tag.INTEGER)
                || this.token.getTag().equals(Tag.LITERAL)
                || this.token.getTag().equals(Tag.OPEN_PAR)) {
            factor();
        } else if (this.token.getTag().equals(Tag.NOT)) {
            eat(Tag.NOT);
            factor();
        } else if (this.token.getTag().equals(Tag.MINUS)) {
            eat(Tag.MINUS);
            factor();
        }
    }

    //factor ::= identifier | constant | "(" expression ")"
    void factor() throws IOException {
        if (this.token.getTag().equals(Tag.ID)) {
            identifier();
        } else if (this.token.getTag().equals(Tag.FLOATING)
                || this.token.getTag().equals(Tag.INTEGER)
                || this.token.getTag().equals(Tag.LITERAL)) {
            constant();
        } else if (this.token.getTag().equals(Tag.OPEN_PAR)) {
            eat(Tag.OPEN_PAR);
            expression();
            eat(Tag.CLOSE_PAR);
        }
    }

    //relop ::= "==" | ">" | ">=" | "<" | "<=" | "<>"
    void relop() throws IOException {
        if (this.token.getTag().equals(Tag.COMPARATION)) {
            eat(Tag.COMPARATION);
        } else if (this.token.getTag().equals(Tag.GREATHER_EQUAL)) {
            eat(Tag.GREATHER_EQUAL);
        } else if (this.token.getTag().equals(Tag.GREATHER_THAN)) {
            eat(Tag.GREATHER_THAN);
        } else if (this.token.getTag().equals(Tag.LESS_EQUAL)) {
            eat(Tag.LESS_EQUAL);
        } else if (this.token.getTag().equals(Tag.LESS_THAN)) {
            eat(Tag.LESS_THAN);
        } else if (this.token.getTag().equals(Tag.DIFF)) {
            eat(Tag.DIFF);
        } else {
            error("relop", this.token.getTag());
        }

    }

    //addop ::= "+" | "-" | or
    void addop() throws IOException {
        if (this.token.getTag().equals(Tag.SUM)) {
            eat(Tag.SUM);
        } else if (this.token.getTag().equals(Tag.MINUS)) {
            eat(Tag.MINUS);
        } else if (this.token.getTag().equals(Tag.OR)) {
            eat(Tag.OR);
        } else {
            error("addop", this.token.getTag());
        }
    }

    //mulop ::= "*" | "/" | and
    void mulop() throws IOException {
        if (this.token.getTag().equals(Tag.MULT)) {
            eat(Tag.MULT);
        } else if (this.token.getTag().equals(Tag.DIV)) {
            eat(Tag.DIV);
        } else if (this.token.getTag().equals(Tag.AND)) {
            eat(Tag.AND);
        } else {
            error("mulop", this.token.getTag());
        }
    }

    //constant ::= integer_const | float_const | literal
    void constant() throws IOException {
        if (this.token.getTag().equals(Tag.INTEGER)) {
            integer_const();
        } else if (this.token.getTag().equals(Tag.FLOATING)) {
            float_const();
        } else if (this.token.getTag().equals(Tag.LITERAL)) {
            literal();
        } else {
            error("CONSTANT", this.token.getTag());
        }
    }

    //integer_const ::= digit {digit}
    void integer_const() throws IOException {
        if (this.token.getTag().equals(Tag.INTEGER)) {
            eat(Tag.INTEGER);
        } else {
            error(Tag.INTEGER, this.token.getTag());
        }
    }

    //float_const ::= digit{digit} “.”digit{digit}
    void float_const() throws IOException {
        if (this.token.getTag().equals(Tag.FLOATING)) {
            eat(Tag.FLOATING);
        } else {
            error(Tag.FLOATING, this.token.getTag());
        }
    }

    //literal ::= " “" {caractere} "”"
    void literal() throws IOException {
        if (this.token.getTag().equals(Tag.LITERAL)) {
            eat(Tag.LITERAL);
        } else {
            error(Tag.LITERAL, this.token.getTag());
        }
    }

    //identifier ::= letter {letter | digit }
    void identifier() throws IOException {
        if (this.token.getTag().equals(Tag.ID)) {
            eat(Tag.ID);
        } else {
            error(Tag.ID, this.token.getTag());
        }
    }

}
