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
    
    public enum State{
        START,INNUM, INID, MAYBECOMMENT, INCOMMENT, LEAVECOMMENT, DONE
    }
    
    private BufferedReader inFile;
    private Token nextToken;
    private String line;
    private int linepos;
    
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

    private String getNextChar(){
        //What I want to do
        /*if the line still has characters get the next one
        if it doesn't get a new line
        if there are no new lines return EOF wait wah?
        */
        String nextChar = "";
        
        try{
            //if the position is greater than the length
            if(linepos > line.length()-1){
                //get new line
                if((line = inFile.readLine()) != null){
                    linepos = 0;
                    nextChar = String.valueOf(line.charAt(linepos++));
                }
                else{
                    //EOF!
                    nextChar = null;
                }
            }
            //Get new line
            else{
                nextChar = String.valueOf(line.charAt(linepos++));
            }
        }
        //Do specific exceptions later
        catch(Exception e){
            
        }
        return nextChar;  
    }
    
    private void ungetNextChar(){
        linepos--;
    }
    
    private Token scanToken() {
        State currState = State.START;
        Token.TokenType currentToken;
        boolean save;
        
        while(currState != State.DONE){
            save = true;
            switch(currState){
                case START:
                    break;
                case INNUM:
                    break;
                case INID:
                    break;
                case MAYBECOMMENT:
                    break;
                case INCOMMENT:
                    break;
                case LEAVECOMMENT:
                    break;
            }
        }
        if(currState == State.DONE){
            //Check for special token
            Token next = viewNextToken();
            //Create token
           
            //Return token
            //return token;
        }
        return null;
    }
}
