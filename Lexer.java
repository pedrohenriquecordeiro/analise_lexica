/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author pedro
 */
public class Lexer {
    public static int line = 1;
    private char ch = ' ';
    private FileReader file;
    
    private Hashtable words = new Hashtable();
    
    public Lexer(String fileName) throws FileNotFoundException{
        this.file = new FileReader(fileName);
        
        //insere palavras reservadas na hashtable
        reserve(new Word("start",Tag.START));
        reserve(new Word("exit",Tag.EXIT));
        reserve(new Word("int",Tag.INT));
        reserve(new Word("float",Tag.FLOAT));
        reserve(new Word("if",Tag.IF));
        reserve(new Word("then",Tag.THEN));
        reserve(new Word("else",Tag.ELSE));
        reserve(new Word("end",Tag.END));
        reserve(new Word("do",Tag.DO));
        reserve(new Word("while",Tag.WHILE));
        reserve(new Word("end",Tag.END));
        reserve(new Word("scan",Tag.SCAN));
        reserve(new Word("print",Tag.PRINT));
    }
    
    
    private void reserve(Word word){
        this.words.put(word.getLexeme(),word);
    }
    
    private void readch() throws IOException{
        this.ch = (char) file.read();
    }
    
    private boolean readch(char character) throws IOException{
        readch();
        if(this.ch != character){
            return false;
        }else{
            return true;
        }
    }
    
    public Token scan() throws IOException{ 
        //desconsidera delimitadores na entrada
        for(;;readch()){
            if(this.ch == ' ' ||
                    this.ch == '\t'||
                        this.ch == '\r'||
                            this.ch == '\b'){
                continue;
            }else if( this.ch == '\n' ){
                this.line++;
            }else{
                break;
            }
        }
        
        switch(this.ch){
            case '=':
                if(readch('=')){
                    return Word.comparation;
                }else{
                    return Word.equal;
                }
            case '<':
                readch();
                switch(this.ch){
                    case '>':
                        return Word.diff;
                    case '=':
                        return Word.less_equal;
                    default:
                        return Word.less_than;
                } 
            case ';':
                return Word.semicolon;
                
        }
        
        //numero
        if(Character.isDigit(this.ch)){
            int value = 0;
            do{
                value = 10*value + Character.digit(this.ch, 10);
                readch();
            }while(Character.isDigit(this.ch));
            return new Num(value);
 
        }
        
        //identificadores
        if(Character.isLetter(this.ch)){
            StringBuffer stringBuffer = new StringBuffer();
            do{
                stringBuffer.append(this.ch);
                readch();
            }while(Character.isLetterOrDigit(this.ch));
            
            String string = stringBuffer.toString();
            Word word = (Word)words.get(string);
            
            if(word != null){
                return word;
            }else{
                word = new Word(string,Tag.ID);
                words.put(string,word);
                return word;
            }
            
        }
        
        Token token = new Token(this.ch);
        this.ch = ' ';
        return token;
    }
    
}