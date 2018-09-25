/**
 *
 * @author pedro
 */
public class Tag {
    public final static int 
            // palavras reservadas
            EXIT            = 0,
            START           = 1,
            // tipos
            INT             = 2,
            FLOAT           = 3,
            STRING          = 4,
            // controle de fluxo
            IF              = 5,
            THEN            = 6,
            ELSE            = 7,
            END             = 8,
            // repeticao
            DO              = 9,
            WHILE           = 10,
            // IO
            SCAN            = 11,
            PRINT           = 12,
            // booleanos
            NOT             = 13,
            OR              = 14,
            AND             = 15,
            //operadores
            // +
            SUM             = 16,
            // -
            MINUS           = 17,
            // * 
            MULT            = 18,
            // /
            DIV             = 19,
            // ==
            COMPARATION     = 20,
            // =
            EQUAL           = 21,
            // >
            GREATHER_THAN   = 22,
            // <
            LESS_THAN       = 23,
            // >=
            GREATHER_EQUAL  = 24,
            // <=
            LESS_EQUAL      = 25,
            // <>
            DIFF            = 26,
            // ;
            SEMICOLON       = 27,
            // ,
            COMMA           = 28,
            // (  )
            OPEN_PAR        = 29,
            CLOSE_PAR       = 30,
            // {  }
            OPEN_C          = 31,
            CLOSE_C         = 32,
            // '.'
            DOT             = 33,
            // contante literal            
            LITERAL           = 34,
            // contantes numericas
            NUM             = 35,
            // identificadores
            ID              = 36;
            
}
