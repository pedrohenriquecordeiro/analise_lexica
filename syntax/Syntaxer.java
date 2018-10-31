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
            error(token_lexeme , this.token.lexeme);
        }
    }

    private void error(String token_expected , String token_gived) {
        // parametro = token recebido
        System.out.println("Geted    : " + token_expected);
        System.out.println("Expected : " + token_gived);
    }
    
    // production
    
    void program() throws IOException{
        if(this.token.lexeme.equals(Tag.START)){
                eat(Tag.START);
                if(  this.token.lexeme.equals(Tag.INT) ||
                        this.token.lexeme.equals(Tag.FLOAT) ||
                        this.token.lexeme.equals(Tag.STRING)){
                       decl_list(); 
                }
                stmt_list();
                eat(Tag.END);
        }else{
            error(Tag.START,this.token.lexeme);
        }
    }
    
    void decl_list() throws IOException{
        
        do{
            decl();
        }while( this.token.lexeme.equals(Tag.INT) ||
                this.token.lexeme.equals(Tag.FLOAT) ||
                this.token.lexeme.equals(Tag.STRING));
        
    }
    
    void stmt_list() throws IOException{
        do{
            stmt();
        }while(this.token.lexeme.equals(Tag.IF) ||
                this.token.lexeme.equals(Tag.ID) ||
                this.token.lexeme.equals(Tag.WHILE)||
                this.token.lexeme.equals(Tag.SCAN) ||
                this.token.lexeme.equals(Tag.PRINT));
    }
    
    void stmt() throws IOException{
        if(this.token.lexeme.equals(Tag.ID)){
            assign_stmt();
            eat(Tag.SEMICOLON);
        }else if(this.token.lexeme.equals(Tag.IF)){
            if_stmt();
        }else if(this.token.lexeme.equals(Tag.DO)){
            while_stmt();
        }else if(this.token.lexeme.equals(Tag.SCAN)){
            read_stmt();
            eat(Tag.SEMICOLON);
        }else if(this.token.lexeme.equals(Tag.PRINT)){
            write_stmt();
            eat(Tag.SEMICOLON);
        }else{
            error(Tag.ID,this.token.lexeme);
        }
    }
    
    void decl() throws IOException{
        type();
        ident_list();
        eat(Tag.SEMICOLON);
    }
    
    void type() throws IOException{
        if(this.token.lexeme.equals(Tag.INT)){
            eat(Tag.INT);
        }else if(this.token.lexeme.equals(Tag.FLOAT)){
            eat(Tag.FLOAT);
        }else if(this.token.lexeme.equals(Tag.STRING)){
            eat(Tag.STRING);
        }else{
            error(Tag.INT,this.token.lexeme);
        }
    }
    
    void ident_list(){
        
    }
    void identifier() throws IOException{
       if(this.token.lexeme.equals(Tag.ID)){
            eat(Tag.ID);
       }else{   
            error(Tag.ID,this.token.lexeme);
       }
    }
    
    void literal() throws IOException{
        if(this.token.lexeme.equals(Tag.LITERAL)){
            eat(Tag.LITERAL);
        }else{
            error(Tag.LITERAL,this.token.lexeme);
        }
    }
    
    void float_const() throws IOException{
        if(this.token.lexeme.equals(Tag.FLOATING)){
            eat(Tag.FLOATING);
        }else{
            error(Tag.FLOATING,this.token.lexeme);
        }
    }
    
    void integer_const() throws IOException{
        if(this.token.lexeme.equals(Tag.INTEGER)){
                eat(Tag.INTEGER);
        }else{
            error(Tag.INTEGER,this.token.lexeme);
        }
    }
    
    
}
