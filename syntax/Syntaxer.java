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
 * @author pedro
 * 30/10/18
 */ 
public class Syntaxer {
    
    final Tag tag;
    Token token;
    Lexer lexer;
    
    public Syntaxer(String filename) throws IOException{
        this.tag = new Tag();
        this.lexer = new Lexer(filename);
        this.token = this.lexer.scan();
    }
    
    void advance() throws IOException{
        this.token = this.lexer.scan();
    }
    
    void eat(String token_lexeme) throws IOException{
        if(token.tag.equals(this.token.lexeme)){
            advance();
        }else{
            error(token.lexeme);
        }
    }

    private void error(String token_lexeme) {
        // parametro = token recebido
        System.out.println("Geted    : " + this.token.lexeme);
        System.out.println("Expected : " + token_lexeme);
    }
    
    // production
    
    void program() throws IOException{
        switch(this.token.lexeme){
            case Tag.START:
                eat(Tag.START);
                if( this.token.lexeme.equals(Tag.INT) ||
                    this.token.lexeme.equals(Tag.FLOAT) ||
                    this.token.lexeme.equals(Tag.STRING)){
                    do{
                       decl_list(); 
                    }while( this.token.lexeme.equals(Tag.INT) ||
                            this.token.lexeme.equals(Tag.FLOAT) ||
                            this.token.lexeme.equals(Tag.STRING));
                }
                stmt_list();
                eat(Tag.END);
            default: error(this.token.lexeme);
        }
    }
    
    void identifier() throws IOException{
        switch(this.token.lexeme){
            case Tag.ID :
                eat(Tag.ID);
                break;
            default: error(this.token.lexeme);
        }
    }
    
    void literal() throws IOException{
        switch(this.token.lexeme){
            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;
            default : error(this.token.lexeme);
        }
    }
    
    void float_const() throws IOException{
        switch(this.token.lexeme){
            case Tag.FLOATING:
                eat(Tag.FLOATING);
                break;
            default : error(this.token.lexeme);
        }
    }
    
    void integer_const() throws IOException{
        switch(this.token.lexeme){
            case Tag.INTEGER:
                eat(Tag.INTEGER);
                break;
            default : error(this.token.lexeme);
        }
    }
    
    
}
