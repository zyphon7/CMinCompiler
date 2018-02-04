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
        INT_TOKEN,
        IF_TOKEN,
        ELSE_TOKEN,
        RETURN_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        //Maybe?
        ID_TOKEN,
        NUM_TOKEN,
        LETTER_TOKEN,
        DIGIT_TOKEN,
        PLUS_TOKEN,
        MINUS_TOKEN,
        MULTI_TOKEN,
        DIVIDE_TOKEN,
        LESS_TOKEN,
        LESSEQ_TOKEN,
        GREATER_TOKEN,
        GREATEREQ_TOKEN,
        DOUBLEEQUAL_TOKEN,
        NOTEQUAL_TOKEN,
        EQUAL_TOKEN,
        SEMICOLIN_TOKEN,
        COMMA_TOKEN,
        LP_TOKEN, //(
        RP_TOKEN,
        LBRACKET_TOKEN, //[
        RBRACKET_TOKEN,
        LCURLY_TOKEN, //{
        RCURLY_TOKEN,
        BEGINCOMMENT_TOKEN,
        ENDCOMMENT_TOKEN,
        ERROR_TOKEN,
        EOF_TOKEN
    }

    
    private TokenType tokenType;
    private Object tokenData;

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
