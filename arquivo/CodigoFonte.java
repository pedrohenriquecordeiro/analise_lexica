/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author pedro
 */
public class CodigoFonte {
    
    File file;
    RandomAccessFile randomAccessFile;
    
    int inteiro;
    
    public CodigoFonte(String nome) throws FileNotFoundException{
        this.file = new File(nome);
        this.randomAccessFile = new RandomAccessFile(this.file,"r");
    }
    
    // retorna o valor numerico do caracter 
    public int getValor() throws IOException{
         this.inteiro = this.randomAccessFile.read();
         return this.inteiro;
    }

    //retorna o valor ascii do caracter
    public char getCaracter(){
        return (char) this.inteiro;
    }
    
    public void retrocederPonteiro(int posicoes) throws IOException{
        long posicaoAtualPonteiro = this.randomAccessFile.getFilePointer();
        this.randomAccessFile.seek( posicaoAtualPonteiro - posicoes );
    }

}
