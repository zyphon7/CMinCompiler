/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

import CminScanner.Token.TokenType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
    private boolean newFile;
    private int lineLength;

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
        newFile = true;
        lineLength = -1;
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
        String nextChar = "";
        int temp;
        try{
            temp = inFile.read();
            if(temp != -1){
                nextChar = String.valueOf((char)temp);
                //Mark our spot to return too
                inFile.mark(1);
            }
            else{
                nextChar = null;
            }
            /*
            //if the position is greater than the length
            if(!(linepos < lineLength)){
                //get new line
                if((line = inFile.readLine()) != null){
                    /*linepos = 0;
                    lineLength = line.length()-1;
                    nextChar = String.valueOf(line.charAt(linepos++));
                    System.out.println(nextChar);
                    
                }
                else{
                    //EOF!
                    nextChar = null;
                }
            }
            //Get new line
            else{
                nextChar = String.valueOf(line.charAt(linepos++));
                System.out.println(nextChar);
            }*/
        }
        //Do specific exceptions later
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(nextChar);
        return nextChar;  
    }
    
    private void ungetNextChar(){
        try{
            inFile.reset();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        //linepos--;
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
                            save = false;
                        }
                        else if(c == '<'){
                            currState = State.LESSTHAN;
                            save = false;
                        }
                        else if(c == '>'){
                            currState = State.GREATERTHAN;
                            save = false;
                        }
                        else if(c == '='){
                            currState = State.ASSIGN;
                            save = false;
                        }
                        else if(c == '!'){
                            currState = State.NOTEQ;
                            save = false;
                        }
                        else if(c == ' ' || c == '\t' || c == '\n'){
                            save = false;
                        }
                        else{
                            save = false;
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
                        save = false;
                        if(c == '*'){
                            currState = State.INCOMMENT;
                        }
                        else{
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
                        else if(c != '*'){
                            currState = State.INCOMMENT;
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
                currState = State.DONE;
                currToken.setTokenType(TokenType.EOF);
            }
            if(save){
                String temp = tokenString.concat(s);
                tokenString = temp;
            }
            if(currState == State.DONE){
                if(currToken.getTokenType() == Token.TokenType.ID){
                   Token.TokenType t = reservedKeywords.get(tokenString);
                   if(t != null){
                       currToken.setTokenType(t);
                       tokenString = "";
                   }
                }
                currToken.setTokenData(tokenString);
                printToken(currToken);
            }
            /*if(TraceParse){*/
<<<<<<< HEAD
                
=======
                //System.out.println("LISTING:");
                printToken(currToken);
>>>>>>> c3058cf7a34f019b95595f91fe995b9ad109a34e
            /*}*/
        }
        return currToken;
    }
    
    private void printToken(Token current){
        try{
            String name = "tokens.csv";
            File output = new File(name);
            
            //if newFile = true create new file otherwise append to the file
            PrintWriter writer = new PrintWriter(new FileOutputStream(output), 
                    !newFile /*append=true/false*/);
                        
            if(newFile == true){
                //print header columns first time
                writer.println("Token Type|Token Data|");
            }
            writer.println(current.getTokenType()+"|");
            //Could do very long if statement testing to see if we have correct 
            //types but if our scanner is working properly this will be fine
            if(current.getTokenData() != null){
                writer.println(current.getTokenData()+"|");
            }
            newFile = false;
            writer.close();
        }
       catch(IOException e){
           e.printStackTrace();
       }
    }    
}
