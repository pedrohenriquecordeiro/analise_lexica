

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
            
            
            
            Token token = lexer.scan();
            System.out.println(token.toString());
            token = lexer.scan();
            System.out.println(token.toString());
            token = lexer.scan();
            System.out.println(token.toString());
            token = lexer.scan();
            System.out.println(token.toString());
            token = lexer.scan();
            System.out.println(token.toString());
           
            
         
            
        } catch (FileNotFoundException e1) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
        }catch(IOException e2){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e2);  
        }
        
    }

}
