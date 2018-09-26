/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Table_of_Symbols.Env;
import Tokens.Symbol;
import Tokens.Tag;
import Tokens.Token;
import Tokens.IntegerNum;
import Tokens.FloatNum;
import Tokens.Word;
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

/**
 *
 * @author pedro
 */
public class Lexer {

    public static int line = 1;
    private char ch = ' ';
    private File file;
    private RandomAccessFile randomAccessFile;
    StringBuffer stringBuffer;

    private Hashtable words = new Hashtable();
    
    private Env env;

    public Lexer(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.randomAccessFile = new RandomAccessFile(this.file, "r");
        this.stringBuffer = new StringBuffer();
        this.env = new Env(null);

        //inserindo palavras reservadas na hashtable
        reserve(new Word(Tag.START,"start"));
        reserve(new Word(Tag.STRING,"string"));
        reserve(new Word(Tag.EXIT , "exit"));
        reserve(new Word(Tag.INT, "int"));
        reserve(new Word(Tag.FLOAT, "float"));
        reserve(new Word(Tag.IF, "if"));
        reserve(new Word(Tag.THEN, "then"));
        reserve(new Word(Tag.ELSE, "else"));
        reserve(new Word(Tag.END, "end"));
        reserve(new Word(Tag.DO, "do"));
        reserve(new Word(Tag.WHILE, "while"));
        reserve(new Word(Tag.END, "end"));
        reserve(new Word(Tag.SCAN, "scan"));
        reserve(new Word(Tag.PRINT, "print"));
        reserve(new Word(Tag.AND, "and"));
        reserve(new Word(Tag.OR, "or"));
        reserve(new Word(Tag.NOT, "not"));
    }

    private void reserve(Word word) {
        this.words.put(word.getLexeme(), word);
    }

    private void readch() throws IOException {
        this.ch = (char) randomAccessFile.read();
    }

    private boolean readch(char character) throws IOException {
        readch();
        if (this.ch != character) {
            return false;
        } else {
            return true;
        }
    }

    public Token scan() throws IOException {
        //desconsidera delimitadores na entrada
        do {
            readch();
            if (this.ch == ' '
                    || this.ch == '\t'
                    || this.ch == '\r'
                    || this.ch == '\b') {
                continue;
            } else if (this.ch == '\n') {
                this.line++;
            } else {
                break;
            }
        } while (true);

        //operadores    
        switch (this.ch) {
            case '=':
                if (readch('=')) {
                    return Symbol.comparation;
                } else {
                    return Symbol.equal;
                }

            case ';':
                return Symbol.semicolon;

            case ',':
                return Symbol.comma;

            case '.':
                return Symbol.dot;

            case '“':
                this.stringBuffer.delete(0, stringBuffer.length());
                while (true) {
                    readch();
                    if (this.ch == '"' || this.ch == '”') {
                        break;
                    } else {
                        stringBuffer.append(this.ch);
                    }
                }
                return new Word(Tag.LITERAL , stringBuffer.toString() );
                
            case '"':
                this.stringBuffer.delete(0, stringBuffer.length());
                while (true) {
                    readch();
                    if (this.ch == '"' || this.ch == '”') {
                        break;
                    } else {
                        stringBuffer.append(this.ch);
                    }
                }
                return new Word(Tag.LITERAL , stringBuffer.toString() );

            case '>':
                readch();
                if (this.ch == '=') {
                    return Symbol.greather_equal;
                } else {
                    return Symbol.greather_than;
                }

            case '<':
                readch();
                if (this.ch == '>') {
                    return Symbol.diff;
                } else if (this.ch == '=') {
                    return Symbol.less_equal;
                } else {
                    return Symbol.less_than;
                }

            case '+':
                return Symbol.sum;

            case '-':
                return Symbol.minus;

            case '*':
                return Symbol.mult;

            case '/':
                return Symbol.div;

            case '(':
                return Symbol.open_par;

            case ')':
                return Symbol.close_par;

            case '{':
                while (true) {
                    readch();
                    if (this.ch == '}') {
                        break;
                    } 
                }
               return Symbol.open_c; 
               
        }

        // constante numericas
        if (Character.isDigit(this.ch)) {
            this.stringBuffer.delete(0, this.stringBuffer.length());
            do {
                this.stringBuffer.append(this.ch);
                readch();
            } while (Character.isDigit(this.ch) || this.ch == '.');

            ComeBackOne();

            int number_dots = countOccurrences(this.stringBuffer.toString(), '.');
            
            if (number_dots <= 1) {
                if (this.stringBuffer.lastIndexOf(".") == -1) {
                    return new IntegerNum(Integer.parseInt(this.stringBuffer.toString()));
                } else {
                    return new FloatNum(Float.parseFloat(this.stringBuffer.toString()));
                }
            }

        }

        //identificadore ou palavras reservadas
        if (Character.isLetter(this.ch)) {
            this.stringBuffer.delete(0, this.stringBuffer.length());
            do {
                this.stringBuffer.append(this.ch);
                readch();
            } while (Character.isLetterOrDigit(this.ch));

            ComeBackOne();

            String string = stringBuffer.toString();
            Word word = (Word) words.get(string);

            if (word != null) {
                return word;
            } else {
                word = new Word(Tag.ID , string);
                // insere identificador na tabela de simbolos
                this.words.put(string,word);
                return word;
            }
        }

        Token token = new Token(Tag.UNKNOWN);
        //System.out.println("aqui  ->  " + this.ch);
        this.ch = ' ';
        return token;
    }

    private void ComeBackOne() throws IOException {
        // retorna o ponteiro do arquivo em UMA posicao
        long posicaoCorrentePonteiro = this.randomAccessFile.getFilePointer();
        this.randomAccessFile.seek(posicaoCorrentePonteiro - 1);
    }

    private int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

}
