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
        // considera que o valor nesse caso eh float
        super(Tag.FLOATING);
        this.value = value;

    }

    public IntegerNum(int value, int tag) {
        super(tag);
        this.value = value;
    }

    public IntegerNum(int value, int tag, String name) {
        super(tag, name);
        this.value = value;
    }

    public int toStringValue() {
        return value;
    }
    
    
    
}
