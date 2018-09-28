package Table_of_Symbols;

import Tokens.Token;
import Tokens.Word;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author pedro
 */

public class Env {
    private Hashtable<String, Word> table;
    private Env prev;
    
    public Env(Env env){
        this.table = new Hashtable<String, Word>();
        this.prev = env;
    }
    
    public void put(String key,Word word){
        if(!this.table.containsKey(key)){
            this.table.put(key,word);
        }
    }
    
    public void delete(String key){
        if(!this.table.containsKey(key)){
            this.table.remove(key);
        }
    }
    
    public boolean containsThisKey(String key){
        return this.table.containsKey(key);
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
    
    public void showHashTable(){
        ArrayList<Word> arrayList = new ArrayList<Word>(this.table.values());
        for(Word w  : arrayList){
            System.out.println(w.getLexeme());
        }
    }

}
