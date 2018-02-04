/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

/**
 *
 * @author Spencer
 */
public class Token {
 
    public enum TokenType {
        INT,
        IF,
        ELSE,
        RETURN,
        VOID,
        WHILE,
        //Maybe?
        ID,
        NUM,
        LETTER,
        DIGIT,
        PLUS,
        MINUS,
        MULTI,
        DIVIDE,
        LESS,
        LESSEQ,
        GREATER,
        GREATEREQ,
        DOUBLEEQUAL,
        NOTEQUAL,
        EQUAL,
        SEMICOLIN,
        COMMA,
        LP, //(
        RP,
        LBRACKET, //[
        RBRACKET,
        LCURLY, //{
        RCURLY,
        BEGINCOMMENT,
        ENDCOMMENT,
        ERROR,
        EOF
    }

    
    private TokenType tokenType;
    private Object tokenData;

    //e.g only need type if its a ;
    public Token (TokenType type) {
        this (type, null);
    }

    public Token (TokenType type, Object data) {
        tokenType = type;
        tokenData = data;
    }

    
       /**
     * @return the tokenType
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * @return the tokenData
     */
    public Object getTokenData() {
        return tokenData;
    }

    /**
     * @param tokenData the tokenData to set
     */
    public void setTokenData(Object tokenData) {
        this.tokenData = tokenData;
    }
    
}
