

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
            String token_str;
            int numero_token = 0;
            
            do{
                numero_token++;
                if(numero_token >= 50){
                    break;
                }
                token = lexer.scan();
                token_str = token.toString();
                System.out.println(token_str);
            }while(!token_str.equals("65535"));
            
         
            
        } catch (FileNotFoundException e1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
        }catch(IOException e2){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e2);  
        }
        
    }

}
