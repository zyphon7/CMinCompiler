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
        START,INNUM, INID, MAYBECOMMENT, INCOMMENT, LEAVECOMMENT, LESSTHAN,
        LESSTHANEQ, GREATERTHAN, GREATERTHANEQ, ASSIGN, EQ, NOTEQ, DONE
    }
    
    private BufferedReader inFile;
    private Token nextToken;
    private String line;
    private int linepos;

    private final Map<String, Token.TokenType> reservedKeywords = new HashMap<String, Token.TokenType>()
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
        String tokenString = "";
        State currState = State.START;
        Token currToken = new Token();
        boolean save;
        
        while(currState != State.DONE){
            String s = getNextChar();
            if(s != null){
                char c = s.charAt(0);
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
                        else if(c == '<'){
                            currState = State.LESSTHAN;
                        }
                        else if(c == '>'){
                            currState = State.GREATERTHAN;
                        }
                        else if(c == '='){
                            currState = State.ASSIGN;
                        }
                        else if(c == '!'){
                            currState = State.NOTEQ;
                        }
                        else if(c == ' ' || c == '\t' || c == '\n'){
                            save = false;
                        }
                        else{
                            currState = State.DONE;
                            switch(c){
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
                            ungetNextChar();
                            save = false;
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.NUM);
                        }
                        break;
                    case INID:
                        if(!isLetter(c)){
                            ungetNextChar();
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
                    case LESSTHAN:
                        save = false;
                        if(c == '='){
                            currState = State.LESSTHANEQ;   
                        }
                        else{
                            ungetNextChar();
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.LESS);
                        }
                        break;
                    case LESSTHANEQ:
                        save = false;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.LESSEQ);
                        break;
                    case GREATERTHAN:
                        save = false;
                        if(c == '='){
                            currState = State.GREATERTHANEQ;
                        }
                        else{
                            ungetNextChar();
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.GREATER);
                        }
                        break;
                    case GREATERTHANEQ:
                        save = false;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.GREATEREQ);
                        break;
                    case ASSIGN:
                        save = false;
                        if(c == '='){
                            currState = State.EQ;
                        }
                        else{
                            ungetNextChar();
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.EQUAL);
                        }
                        break;
                    case EQ:
                        save = false;
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.DOUBLEEQUAL);
                        break;
                    case NOTEQ:
                        save = false;
                        currState = State.DONE;
                        if(c == '='){
                            currToken.setTokenType(TokenType.NOTEQUAL);
                        }
                        else{
                            currToken.setTokenType(TokenType.ERROR);
                        }
                        break;
                    case DONE:
                    default: // SHOULD NOT HAPPEN
                        System.out.println("SCANNER BUG!");
                        currState = State.DONE;
                        currToken.setTokenType(TokenType.ERROR);
                        break;
                }
            }
            else{
                save = false;
                currToken.setTokenType(TokenType.EOF);
            }
            if(save){
                tokenString.concat(s);
            }
            if(currState == State.DONE){
                if(currToken.getTokenType() == Token.TokenType.ID){
                   Token.TokenType t = reservedKeywords.get(tokenString);
                   if(t != null){
                       currToken.setTokenType(t);
                   }
                }
                currToken.setTokenData(tokenString);
            }
            /*if(TraceParse){*/
                System.out.println("LISTING:");
                //TODO:printToken(currToken);
            /*}*/
        }
        return currToken;
    }
}
