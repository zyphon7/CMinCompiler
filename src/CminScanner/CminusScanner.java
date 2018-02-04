/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

import java.io.BufferedReader;

/**
 *
 * @author Annie
 */
public class CminusScanner implements Scanner{
    private BufferedReader inFile;
    private Token nextToken;
    
    public CminusScanner(BufferedReader file){
        inFile = file;
        nextToken = scanToken();   
    }
    
    public Token getNextToken(){
        Token returnToken = nextToken;
        if(nextToken.getTokenType()!= Token.TokenType.EOF){
            nextToken = scanToken();
        }
        return returnToken;
    }
    
    public Token viewNextToken(){
        return nextToken;
    }

    private Token scanToken() {
        //TODO!
        return null;
    }
}
