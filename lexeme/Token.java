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
public class Token {
    public final int tag;
    
    public Token(int tag){
        this.tag = tag;
    }
    
    public String toString(){
        return "" + tag;
    }
}
