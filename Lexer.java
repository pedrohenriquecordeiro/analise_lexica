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
        reserve(new Word("start", Tag.START, "START"));
        reserve(new Word("exit", Tag.EXIT, "EXIT"));
        reserve(new Word("int", Tag.INT, "INT"));
        reserve(new Word("float", Tag.FLOAT, "FLOAT"));
        reserve(new Word("if", Tag.IF, "IF"));
        reserve(new Word("then", Tag.THEN, "THEN"));
        reserve(new Word("else", Tag.ELSE, "ELSE"));
        reserve(new Word("end", Tag.END, "END"));
        reserve(new Word("do", Tag.DO, "DO"));
        reserve(new Word("while", Tag.WHILE, "WHILE"));
        reserve(new Word("end", Tag.END, "END"));
        reserve(new Word("scan", Tag.SCAN, "SCAN"));
        reserve(new Word("print", Tag.PRINT, "PRINT"));
        reserve(new Word("and", Tag.AND, "AND"));
        reserve(new Word("or", Tag.OR, "OR"));
        reserve(new Word("not", Tag.NOT, "NOT"));
    }

    private void reserve(Word word) {
        this.words.put(word.toString(), word);
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

            case '"':
                this.stringBuffer.delete(0, stringBuffer.length());
                while (true) {
                    readch();
                    if (this.ch == '"') {
                        break;
                    } else {
                        stringBuffer.append(this.ch);
                    }
                }
                return new Symbol(stringBuffer.toString(), Tag.LITERAL, "LITERAL");

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
                return Symbol.open_c;

            case '}':
                return Symbol.close_c;
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
                    return new IntegerNum(Integer.parseInt(this.stringBuffer.toString()), Tag.INTEGER, "INTEGER");
                } else {
                    return new FloatNum(Float.parseFloat(this.stringBuffer.toString()), Tag.FLOATING, "FLOATING");
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
                word = new Word(string, Tag.ID, "ID");
                // insere identificador na tabela de simbolos
                this.env.put(word,string);
                return word;
            }
        }

        Token token = new Token(this.ch, "UNKNOWN");
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
