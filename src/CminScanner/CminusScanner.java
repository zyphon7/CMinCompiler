/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

import CminScanner.Token.TokenType;
import java.io.BufferedReader;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Token.TokenType> reservedKeywords = new HashMap<String, Token.TokenType>()
{{
     put("else", Token.TokenType.ELSE);
     put("if", Token.TokenType.IF);
     put("int", Token.TokenType.INT);
     put("return", Token.TokenType.RETURN);
     put("void", Token.TokenType.VOID);
     put("while", Token.TokenType.WHILE);
}};

    
    public CminusScanner(BufferedReader file){
        inFile = file;
        nextToken = scanToken();   
    }
    
    public Token getNextToken(){
        Token returnToken = nextToken;
        if(nextToken.getTokenType()!= TokenType.EOF){
            nextToken = scanToken();
        }
        return returnToken;
    }
    
    public Token viewNextToken(){
        return nextToken;
    }

    private Token scanToken() {
        String tokenString = "";
        State currState = State.START;
        Token currToken = new Token();
        boolean save;
        
        while(currState != State.DONE){
            char c = getNextChar();
            save = true;
            switch(currState){
                case START:
                    if(isDigit(c)){
                        currState = State.INNUM;
                    }
                    else if(isLetter(c)){
                        currState = State.INID;
                    }
                    else if(c == '/'){
                        currState = State.MAYBECOMMENT;
                    }
                    else if(c == ' ' || c == '\t' || c == '\n'){
                        save = false;
                    }
                    else{
                        currState = State.DONE;
                        switch(c){
                            case EOF:
                                save = false;
                                currToken.setTokenType(TokenType.EOF);
                                break;
                            case '=':
                                currToken.setTokenType(TokenType.EQUAL);
                                break;
                            case '<':
                                currToken.setTokenType(TokenType.LESS);
                                break;
                            case '+':
                                currToken.setTokenType(TokenType.PLUS);
                                break;
                            case '-':
                                currToken.setTokenType(TokenType.MINUS);
                                break;
                            case '*':
                                currToken.setTokenType(TokenType.MULTI);
                                break;
                            case '(':
                                currToken.setTokenType(TokenType.LP);
                                break;
                            case ')':
                                currToken.setTokenType(TokenType.RP);
                                break;
                            case ',':
                                currToken.setTokenType(TokenType.COMMA);
                                break;
                            case ';':
                                currToken.setTokenType(TokenType.SEMICOLON);
                                break;
                            case '[':
                                currToken.setTokenType(TokenType.LBRACKET);
                                break;
                            case ']':
                                currToken.setTokenType(TokenType.RBRACKET);
                                break;
                            case '{':
                                currToken.setTokenType(TokenType.LCURLY);
                                break;
                            case '}':
                                currToken.setTokenType(TokenType.RCURLY);
                                break;  
                        }
                    }
                    break;
                case INNUM:
                    if(!isDigit(c)){
                        //TODO:unget
                        save = false;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.NUM);
                    }
                    break;
                case INID:
                    if(!isLetter(c)){
                        //TODO:unget
                        save = false;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.ID);
                    }
                    break;
                case MAYBECOMMENT:
                    if(c == '*'){
                        save = false;
                        currState = State.INCOMMENT;
                    }
                    else{
                        save = true;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.DIVIDE);
                    }
                    break;
                case INCOMMENT:
                    save = false;
                    if(c == '*'){
                        currState = State.LEAVECOMMENT;
                    }
                    break;
                case LEAVECOMMENT:
                    save = false;
                    if(c == '/'){
                        currState = State.START;
                    }
                    break;
                case DONE:
                default: // SHOULD NOT HAPPEN
                    System.out.println("SCANNER BUG!");
                    currState = State.DONE;
                    currToken.setTokenType(TokenType.ERROR);
                    break;
            }
            if(save){
                tokenString.concat(String.valueOf(c));
            }
            if(currState == State.DONE){
                if(currToken.getTokenType() == Token.TokenType.ID){
                   Token.TokenType t = reservedKeywords.get(tokenString);
                   if(t != null){
                       currToken.setTokenType(t);
                   }
                }
            }
            /*if(TraceParse){*/
                System.out.println("LISTING:");
                //TODO:printToken(currToken,tokenString);
            /*}*/
        }
        return currToken;
    }
    char getNextChar(){
        return null;
    }
}
