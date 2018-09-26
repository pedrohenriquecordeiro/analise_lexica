package Table_of_Symbols;


import Tokens.Token;
import Tokens.Word;
import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class Env {
    private Hashtable table;
    protected Env prev;
    
    public Env(Env env){
        this.table = new Hashtable();
        this.prev = env;
    }
    
    public void put(Word word,String name){
        this.table.put(name,word);
    }
    
    public Word get(Word word){
        for(Env env = this ; env != null ; env = env.prev ){
            Word found = (Word) env.table.get(word);
            if( found != null){
                return found;
            }
        }
        return null;
    }
    
}
