package semantic;

public enum ErrorType {

    // Variable declaration error
    NO_DECLARATION,

    // Multiple declaration of variable
    MULTIPLE_DECLARATION,

    // Type mismatch errors
    INT_FLOAT_CAST, // int to float error
    INT_STRING_CAST, // int to string error
    FLOAT_INT_CAST, // float to int error
    FLOAT_STRING_CAST, // float to string error
    STRING_INT_CAST, // string to int error
    STRING_FLOAT_CAST, // string to float error

    SINGLE_TO_ARRAY, //single variable to array error
    ARRAY_TO_SINGLE, //array to single variable error
    INVALID_CONDITION //invalid condition error
}
