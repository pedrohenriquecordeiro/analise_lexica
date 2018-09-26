package Tokens;


import Tokens.Tag;
import Tokens.Token;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pedro
 * 
 * 
 */
public class Symbol extends Token {
    
    private String lexeme = "";
    
    public static final Symbol comparation     = new Symbol(Tag.COMPARATION   ,"==");
    public static final Symbol greather_than   = new Symbol(Tag.GREATHER_THAN , ">");
    public static final Symbol greather_equal  = new Symbol(Tag.GREATHER_EQUAL,">=");
    public static final Symbol less_than       = new Symbol(Tag.LESS_THAN     ,"<" );
    public static final Symbol less_equal      = new Symbol(Tag.LESS_EQUAL    ,"<=");
    public static final Symbol diff            = new Symbol(Tag.DIFF          ,"<>");
    public static final Symbol semicolon       = new Symbol(Tag.SEMICOLON     ,";" );
    public static final Symbol comma           = new Symbol(Tag.COMMA         ,"," );
    public static final Symbol dot             = new Symbol(Tag.DOT           ,"." );
    public static final Symbol equal           = new Symbol(Tag.EQUAL         ,"=" );
    public static final Symbol sum             = new Symbol(Tag.SUM           ,"+" );
    public static final Symbol minus           = new Symbol(Tag.MINUS         ,"-" );
    public static final Symbol mult            = new Symbol(Tag.MULT          ,"*" );
    public static final Symbol div             = new Symbol(Tag.DIV           ,"/" );
    public static final Symbol open_par        = new Symbol(Tag.OPEN_PAR      ,"(" );
    public static final Symbol close_par       = new Symbol(Tag.CLOSE_PAR     ,")" );
    public static final Symbol open_c          = new Symbol(Tag.OPEN_C        ,"{" );
    public static final Symbol close_c         = new Symbol(Tag.CLOSE_C       ,"}" );
    
    
    public Symbol(String tag,String lexeme){
        super(tag);
        this.lexeme = lexeme;
    }
    
    
    public String toString(){
        return this.lexeme;
    }
}
