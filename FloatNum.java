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
        // considera que o valor nesse caso eh float
        super(Tag.FLOATING);
        this.value = value;

    }

    public FloatNum(float value, int tag) {
        super(tag);
        this.value = value;
    }

    public FloatNum(float value, int tag, String name) {
        super(tag, name);
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }
    
    public String toString(){
        return "" + this.value;
    }
    
    
}
