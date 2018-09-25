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
    public final int tag;
    public final String name;
    
    public Token(int tag){
        this.tag = tag;
        this.name = "";
    }
    
    public Token(int tag,String name){
        this.tag = tag;
        this.name = name;
    }
    
    public String toString(){
        return "" + this.tag;
    }
    
    public String getName(){
        return this.name;
    }
}
