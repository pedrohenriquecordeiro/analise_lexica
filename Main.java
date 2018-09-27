

import Tokens.Token;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pedro henrique cordeiro
 *
 */
public class Main {

    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer("C:\\Users\\pedro\\Dropbox\\"
                    + "7a período\\Compiladores\\Trabalho\\primeira_parte\\arquivo.txt");
            
            
            
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
                System.out.println( tag + " < " + token.getLexeme() + " >");
                
                
            }while(!tag.equals("EXIT") );
            
         
            
        } catch (FileNotFoundException e1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
        }catch(IOException e2){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e2);  
        }
        
        //8172
        //“Sim”
        //System.out.println('“' - '0');
       // System.out.println('”' - '0');
    }
        
    

}
