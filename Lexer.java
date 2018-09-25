/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

    private Hashtable words = new Hashtable();

    public Lexer(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.randomAccessFile = new RandomAccessFile(this.file, "r");

        //insere palavras reservadas na hashtable
        reserve(new Word("start", Tag.START , "START"));
        reserve(new Word("exit", Tag.EXIT,"EXIT"));
        reserve(new Word("int", Tag.INT,"INT"));
        reserve(new Word("float", Tag.FLOAT,"FLOAT"));
        reserve(new Word("if", Tag.IF,"IF"));
        reserve(new Word("then", Tag.THEN,"THEN"));
        reserve(new Word("else", Tag.ELSE,"ELSE"));
        reserve(new Word("end", Tag.END,"END"));
        reserve(new Word("do", Tag.DO,"DO"));
        reserve(new Word("while", Tag.WHILE,"WHILE"));
        reserve(new Word("end", Tag.END,"END"));
        reserve(new Word("scan", Tag.SCAN,"SCAN"));
        reserve(new Word("print", Tag.PRINT,"PRINT"));
        reserve(new Word("and", Tag.AND,"AND"));
        reserve(new Word("or", Tag.OR,"OR"));
        reserve(new Word("not", Tag.NOT,"NOT"));
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
                    return Word.comparation;
                } else {
                    return Word.equal;
                }

            case ';':
                return Word.semicolon;
                
            case ',':
                return Word.comma;

            case '.':
                return Word.dot;

            case '"':
                StringBuffer stringBuffer = new StringBuffer();
                while(true){
                    readch();
                    if(this.ch == '"'){
                        break;
                    }else{
                        stringBuffer.append(this.ch);
                    }
                }
                return new Word(stringBuffer.toString(),Tag.LITERAL,"LITERAL");
                
               
            case '>':
                readch();
                if (this.ch == '=') {
                    return Word.greather_equal;
                } else {
                    return Word.greather_than;
                }

            case '<':
                readch();
                if (this.ch == '>') {
                    return Word.diff;
                } else if (this.ch == '=') {
                    return Word.less_equal;
                } else {
                    return Word.less_than;
                }

            case '+':
                return Word.sum;

            case '-':
                return Word.minus;

            case '*':
                return Word.mult;

            case '/':
                return Word.div;

            case '(':
                return Word.open_par;

            case ')':
                return Word.close_par;

            case '{':
                return Word.open_c;

            case '}':
                return Word.close_c;
        }

        //numero
        if (Character.isDigit(this.ch)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(this.ch, 10);
                readch();
            } while (Character.isDigit(this.ch));

            ComeBackOne();

            return new Num(value,"NUM");
        }

        //identificadores
        if (Character.isLetter(this.ch)) {
            StringBuffer stringBuffer = new StringBuffer();
            do {
                stringBuffer.append(this.ch);
                readch();
            } while (Character.isLetterOrDigit(this.ch));

            ComeBackOne();

            String string = stringBuffer.toString();
            Word word = (Word) words.get(string);

            if (word != null) {
                return word;
            } else {
                word = new Word(string, Tag.ID,"ID");
                words.put(string, word);
                return word;
            }
        }

        Token token = new Token(this.ch);
        this.ch = ' ';
        return token;
    }

    private void ComeBackOne() throws IOException {
        // retorna o ponteiro do arquivo em UMA posicao
        long posicaoCorrentePonteiro = this.randomAccessFile.getFilePointer();
        this.randomAccessFile.seek(posicaoCorrentePonteiro - 1);
    }

}
