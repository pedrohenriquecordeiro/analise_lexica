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
public class IntegerNum extends Token {

    private final int value;
    
     public IntegerNum(int value) {
        super(Tag.INTEGER);
        this.value = value;

    }

    public String getLexeme(){
        return "" + this.value;
    }
    
     public int getValue() {
        return this.value;
    }
    
    
    
}
