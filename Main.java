import lex.Lexer;
import lex.Token;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import syntax.Syntaxer;

/**
 *
 * @author pedro henrique cordeiro
 *
 */

public class Main {

    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer("./arquivo.txt");

            Token token;
            String lexeme,tag;
            int numero_token = 0;

            do{
                numero_token++;
                if(numero_token >= 300){
                    break;
                }
                // buscar um token
                token = lexer.scan();
                // retorna a tag
                tag = token.getTag();
                //System.out.println( tag + "< " + token.getLexeme() + " >");
            }while(!tag.equals("EXIT") );

            //System.out.println("\n\nTABELA DE SIMBOLOS");
            lexer.getSymbolTable();
            //System.out.println("----------------\n\n");

        } catch (FileNotFoundException e1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
        }catch(IOException e2){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e2);
        }


        try {
            Syntaxer sintaxer = new Syntaxer("./arquivo.txt");
            sintaxer.begin();


        } catch (FileNotFoundException e1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
        }catch(IOException e2){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e2);
        }

    }

}