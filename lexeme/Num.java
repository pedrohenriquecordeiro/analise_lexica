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
public class Num extends Token{
    public int valor;

    public Num(int valor) {
        super(Tag.NUM);
        this.valor = valor;
    }
    
    public String toString(){
        return "" + valor;
    }
    
}
