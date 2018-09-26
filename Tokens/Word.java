package Tokens;

/**
 *
 * @author pedro
 * 
 *   Um word pode ser uma palavra reservada, um ID ou um LITERAL
 * 
 */
public class Word extends Token {
    
    private String lexeme = "";
    
    public Word(String tag) {
        super(tag);
    }
    
    public Word(String tag , String lexeme){
        super(tag);
        this.lexeme = lexeme;
    }
    
    public String getLexeme(){
        return this.lexeme;
    }
    
    
}
