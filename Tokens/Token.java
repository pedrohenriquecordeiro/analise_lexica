package Tokens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pedro
 */
public class Token {
    public final String tag;

    public Token(String tag){
        this.tag = tag;
    }
    
    
    public String toString(){
        return this.tag;
    }
}
