package semantic;

import java.io.IOException;
import java.util.ArrayList;

import syntax.Syntaxer;
import syntaxTree.*;

public class Semantic {

    private Syntaxer syntaxer;
    private ArrayList<decl> declarations;
    private ArrayList<identifier> identifiers;
    private ArrayList<assignStmt> assigns;
    private ArrayList<expression> conditions;

    private int errors;

    public Semantic(String filename) throws IOException {
        this.syntaxer = new Syntaxer(filename);
    }

    // start semantic step
    public void begin() throws IOException{
        this.syntaxer.begin();
        // get all declarations, then check them using checkDeclarations
        //checkDeclarations();

        // get all identifiers, then check them using checkIdentifiers
        //checkIdentifiers();

        // get all assigns, then check them using checkAssings
        //checkAssings();

        // get all conditions, then check them using checkConditions
        //checkConditions();

    }

    // get number of errors
    public int getErrors() {
        return errors;
    }

    // print errors
    public void printErrors(){
    }

    // check program declarations
    public void checkDeclarations(){
        for(int i = 0; i < declarations.size(); i++){
            decl varDecl = declarations.get(i);
            String idName = varDecl.getId().getName();

            for(int j = i + 1; j < declarations.size(); j ++){
                decl _varDecl = declarations.get(j);
                String _idName = _varDecl.getId().getName();

                if(idName.equals(_idName))
                    error(ErrorType.MULTIPLE_DECLARATION, _idName);
            }
        }
    }

    // check program identifiers
    public void checkIdentifiers(){
        for (identifier identifier : identifiers) {
            if(!isIdentifierExists(identifier.getName()))
                error(ErrorType.NO_DECLARATION, identifier.getName());
        }
    }

    // check if a specific identifier name is exists
    private boolean isIdentifierExists(String name){
        for (decl varDecl : declarations) {
            String idName = varDecl.getId().getName();

            if(idName.equals(name))
                return true;
        }
        return false;
    }

    // check program conditions
    private void checkConditions(){

    }

    // check program assigns (type)
    public void checkAssings(){
    }

    // print errors report
    private void error(ErrorType errorType, Object parm){
        errors++;
        switch (errorType) {
            case MULTIPLE_DECLARATION:
                System.err.println("Declaration Error: MULTIPLE_DECLARATION, variable (" + (String) parm + ")");
                break;
            case NO_DECLARATION:
                System.err.println("Declaration Error: NO_DECLARATION, variable (" + (String) parm + ")");
                break;
            case INT_FLOAT_CAST:
                System.err.println("Casting Error: INT_FLOAT_CAST, variable (" + parm + ")");
                break;
            case INT_STRING_CAST:
                System.err.println("Casting Error: INT_STRING_CAST, variable (" + parm + ")");
                break;
            case FLOAT_INT_CAST:
                System.err.println("Casting Error: FLOAT_INT_CAST, variable (" + parm + ")");
                break;
            case FLOAT_STRING_CAST:
                System.err.println("Casting Error: FLOAT_STRING_CAST, variable (" + parm + ")");
                break;
            case STRING_INT_CAST:
                System.err.println("Casting Error: STRING_INT_CAST, variable (" + parm + ")");
                break;
            case STRING_FLOAT_CAST:
                System.err.println("Casting Error: STRING_FLOAT_CAST, variable (" + parm + ")");
                break;
            case ARRAY_TO_SINGLE:
                System.err.println("Invalid Assignment: ARRAY_TO_SINGLE, variable (" + parm + ")");
                break;
            case SINGLE_TO_ARRAY:
                System.err.println("Invalid Assignment: SINGLE_TO_ARRAY, variable (" + parm + ")");
                break;
            case INVALID_CONDITION:
                System.err.println("Invalid Condition: INVALID_CONDITION");
                break;
            default:
                break;
        }
    }

}