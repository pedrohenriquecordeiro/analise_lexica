/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexeme;

/**
 *
 * @author pedro
 */
public class Word extends Token {
    private String lexeme = "";
    
    public static final Word and             = new Word("and",Tag.AND);
    public static final Word or              = new Word("or",Tag.OR);
    public static final Word not             = new Word("not",Tag.NOT);
    public static final Word comparation     = new Word("==",Tag.COMPARATION);
    public static final Word greather_than   = new Word(">",Tag.GREATHER_THAN);
    public static final Word greather_equal  = new Word(">=",Tag.GREATHER_EQUAL);
    public static final Word less_than       = new Word("<",Tag.LESS_THAN);
    public static final Word less_equal      = new Word("<=",Tag.LESS_EQUAL);
    public static final Word diff            = new Word("<>",Tag.DIFF);
    public static final Word semicolon       = new Word(";",Tag.SEMICOLON);
    public static final Word equal           = new Word("=",Tag.EQUAL);
    
    public Word(String lexeme,int tag){
        super(tag);
        this.lexeme = lexeme;
    }
    
    public String toString(){
        return "" + this.lexeme;
    }
    
    public String getLexeme(){
        return this.lexeme;
    }
}
