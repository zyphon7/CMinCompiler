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
        DOUBLE_TOKEN,
        IF_TOKEN,
        EOF_TOKEN
        // rest of tokens ....
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
