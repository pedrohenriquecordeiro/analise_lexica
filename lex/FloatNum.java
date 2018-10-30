package lex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class FloatNum extends Token {
    
    private final float value;
    
     public FloatNum(float value) {
        super(Tag.FLOATING);
        this.value = value;

    }
     
    public float getValue() {
        return this.value;
    }
    
    public String getLexeme(){
        return "" + this.value;
    }
    
    
}
