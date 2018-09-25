/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pedro
 */
public class Word extends Token {
    private String lexeme = "";
    
    public static final Word comparation     = new Word("==",Tag.COMPARATION   ,"COMPARATION");
    public static final Word greather_than   = new Word(">" ,Tag.GREATHER_THAN ,"GREATHER_THAN");
    public static final Word greather_equal  = new Word(">=",Tag.GREATHER_EQUAL,"GREATHER_EQUAL");
    public static final Word less_than       = new Word("<" ,Tag.LESS_THAN     ,"LESS_THAN");
    public static final Word less_equal      = new Word("<=",Tag.LESS_EQUAL    ,"LESS_EQUAL");
    public static final Word diff            = new Word("<>",Tag.DIFF          ,"DIFF");
    public static final Word semicolon       = new Word(";" ,Tag.SEMICOLON     ,"SEMICOLON");
    public static final Word comma           = new Word("," ,Tag.COMMA         ,"COMMA");
    public static final Word dot             = new Word("." ,Tag.DOT           ,"DOT");
    public static final Word equal           = new Word("=" ,Tag.EQUAL         ,"EQUAL");
    public static final Word sum             = new Word("+" ,Tag.SUM           ,"SUM");
    public static final Word minus           = new Word("-" ,Tag.MINUS         ,"MINUS");
    public static final Word mult            = new Word("*" ,Tag.MULT          ,"MULT");
    public static final Word div             = new Word("/" ,Tag.DIV           ,"DIV");
    public static final Word open_par        = new Word("(" ,Tag.OPEN_PAR      ,"OPEN_PAR");
    public static final Word close_par       = new Word(")" ,Tag.CLOSE_PAR     ,"CLOSE_PAR");
    public static final Word open_c          = new Word("{" ,Tag.OPEN_C        ,"OPEN_C");
    public static final Word close_c         = new Word("}" ,Tag.CLOSE_C       ,"CLOSE_C");
    
    
    public Word(String lexeme,int tag){
        super(tag);
        this.lexeme = lexeme;
    }
    
     public Word(String lexeme,int tag,String name){
        super(tag,name);
        this.lexeme = lexeme;
    }
    
    public String toString(){
        return "" + this.lexeme;
    }
    
    public String getLexeme(){
        return this.lexeme;
    }
}
