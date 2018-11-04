package lex;

/*
 * observacao quanto ao RandomAcessFile
 * existe um ponteiro de arquivo, esse ponteiro aponta para um char do arquivo
 * quando damos um read(), o comando retorna o char apontado pelo ponteiro e 
 * incrementa a posicao do ponteiro em 1, ou seja
 * ele passa a apontar pro proximo char, e retornará esse char no comando read()
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

/**
 *
 * @author pedro
 */
public class Lexer {

    public int line = 1;
    private char ch = ' ';
    private char last_ch = ' ';
    private RandomAccessFile randomAccessFile;
    StringBuffer stringBuffer;

    private Hashtable reserved_words = new Hashtable();

    private Env env;

    public Lexer(String fileName) throws FileNotFoundException, IOException {

        this.randomAccessFile = new RandomAccessFile(new File(fileName), "r");
        this.stringBuffer = new StringBuffer();
        this.env = new Env(null);

        //inserindo palavras reservadas na hashtable
        reserve(new Word(Tag.START, "start"));
        reserve(new Word(Tag.STRING, "string"));
        reserve(new Word(Tag.EXIT, "exit"));
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
        this.reserved_words.put(word.getLexeme(), word);
    }

    private void readch() throws IOException {
        this.last_ch = this.ch;
        this.ch = (char) this.randomAccessFile.read();
        //int c = this.ch - '0';
        //System.out.println("readch " + this.ch);
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
        int char_to_int = this.ch - '0';

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

            //case '.':
            //    return Symbol.dot;

            case '"':
                long posicaoAtualPonteiro = this.randomAccessFile.getFilePointer();
                char_to_int = this.ch - '0';
                this.stringBuffer.delete(0, stringBuffer.length());
                while (true) {
                    readch();
                    char_to_int = this.ch - '0';
                    if (this.ch == '"' || char_to_int == 65487) {
                        break;
                    } else {
                        this.stringBuffer.append(this.ch);
                    }
                }
                
                if(char_to_int == 65487){
                    this.randomAccessFile.seek(posicaoAtualPonteiro);
                    return new Token(Tag.UNKNOWN, "" + '"');
                }else{
                    return new Word(Tag.LITERAL, stringBuffer.toString());
                }

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
                    if (this.ch == '}' || this.ch == (char) -1) {
                        break;
                    }
                }
                return Symbol.comment;

        }

        /*
             converting char to integer
             '“' = 99
             '”' = 100
             fim de arquivo = 65487
        
         */
        // convertendo o char corrente para inteiro
        char_to_int = this.ch - '0';

        if (char_to_int == 99) {
            this.stringBuffer.delete(0, stringBuffer.length());
            while (true) {
                readch();
                char_to_int = this.ch - '0';
                if (char_to_int == 100 || char_to_int == 65487) {
                    break;
                } else {
                    stringBuffer.append(this.ch);
                }
            }
            return new Word(Tag.LITERAL, stringBuffer.toString());
        }

        // constante numericas
        if (Character.isDigit(this.ch)) {
            this.stringBuffer.delete(0, this.stringBuffer.length());
            do {
                this.stringBuffer.append(this.ch);
                readch();
            } while (Character.isDigit(this.ch) || this.ch == '.');

            // verificando se eh final de arquivo
            char_to_int = this.ch - '0';
            if(!(char_to_int == 65487)){
                ComeBackOne();
            }

            int number_dots = countOccurrences(this.stringBuffer.toString(), '.');

            if (number_dots <= 1) {
                if (this.stringBuffer.lastIndexOf(".") == -1) {
                    return new IntegerNum(Integer.parseInt(this.stringBuffer.toString()));
                } else {
                    return new FloatNum(Float.parseFloat(this.stringBuffer.toString()));
                }
            }

        }

        //identificadores ou palavras reservadas
        if (Character.isLetter(this.ch)) {
            this.stringBuffer.delete(0, this.stringBuffer.length());
            do {
                this.stringBuffer.append(this.ch);
                readch();
            } while (Character.isLetterOrDigit(this.ch));
            
            // verificando se eh final de arquivo
            char_to_int = this.ch - '0';
            if(!(char_to_int == 65487)){
                ComeBackOne();
            }

            String string = stringBuffer.toString();
            Word word = (Word) reserved_words.get(string);

            if (word != null) {
                return word;
            } else {
                word = new Word(Tag.ID, string);
                // insere identificador na tabela de simbolos
                this.env.put(string, word);
                return word;
            }
        }

        Token token = new Token(Tag.UNKNOWN, "" + this.ch);
        //System.out.println("aqui  ->  " + this.ch);
        //this.ch = ' ';
        return token;
    }

    private void ComeBackOne() throws IOException {
        // retorna o ponteiro do arquivo em UMA posicao
        long posicaoCorrentePonteiro = this.randomAccessFile.getFilePointer();
        this.randomAccessFile.seek(posicaoCorrentePonteiro - 1);
        //readch();
        //System.out.println("in come back one -> " + this.ch);
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
    
    public void getSymbolTable(){
        this.env.showHashTable();
    }
    
    public int getLine(){
        return this.line;
    }

}
