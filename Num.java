/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pedro
 */
public class Num extends Token{
    public int value;

    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    
    public Num(int value,String name) {
        super(Tag.NUM,name);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
    
}
