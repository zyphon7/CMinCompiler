/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

import CminScanner.Token.TokenType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Spencer & Ann
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
    
    /**
     * @param args the command line arguments
     */
    //Just for testing scanner
    public static void main(String[] args) {
        try{
            File cminFile = new File("CminPrograms/program1.txt");
            CminusScanner cminscanner = new CminusScanner(new BufferedReader(new FileReader(cminFile)));
            
            while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                cminscanner.getNextToken();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        } catch (lexicalErrorException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }       
    }

    
    public CminusScanner(BufferedReader file) throws lexicalErrorException{
        inFile = file;
        lineLength = -1;
        String name = "tokens.csv";
        File output = new File(name);
        //Delete the file everytime scanner is run
        if(output.exists()){
            output.delete();
        }
        nextToken = scanToken();
    }
    
    /**
     * @return grab the next token in the file
     */
    public Token getNextToken(){
        Token returnToken = nextToken;
        if(nextToken.getTokenType()!= TokenType.EOF){
            try {
                nextToken = scanToken();
            } catch (lexicalErrorException ex) {
                System.out.println(ex.toString());
                System.exit(1);
            }
        }
        return returnToken;
    }
    
    /**
     * @return view the next token in the file
     */
    public Token viewNextToken(){
        return nextToken;
    }

    /**
     * @return the next character of the input file to determine next token
     */
    private String getNextChar(){
        String nextChar = "";
        int temp;
        try{
            inFile.mark(1);
            temp = inFile.read();
            if(temp == 13){
                inFile.mark(1);
                temp = inFile.read();
            }
            if(temp != -1){
                nextChar = String.valueOf((char)temp);
            }
            else{
                nextChar = null;
            }
        }
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
    }
    
    /**
     * @return scans for the next token
     * @throws lexicalErrorException if a lex error occurs while scanning
     */
    private Token scanToken() throws lexicalErrorException {
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
                        //LEX ERROR i.e. 99i throw exception
                        if(isLetter(c)){
                            throw new lexicalErrorException("Letters cannot occur inside a number. ");
                        }
                        else if(!isDigit(c)){
                            ungetNextChar();
                            save = false;
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.NUM);
                        }
                        
                        break;
                    case INID:
                        //LEX ERROR i.e. hi3
                        if(isDigit(c)){
                            throw new lexicalErrorException("Numbers cannot occur inside an identifier.");
                        }
                        else if(!isLetter(c)){
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
                            ungetNextChar();
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
                            ungetNextChar();
                            save = false;
                            currState = State.DONE;
                            currToken.setTokenType(TokenType.ERROR);
                            currToken.setTokenData("Letters cannot be mixed in with numbers.");
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
                if(currToken.getTokenType() == Token.TokenType.ERROR){
                    System.out.println(currToken.getTokenData());
                    System.exit(1);
                }
                    currToken.setTokenData(tokenString);
               
                printToken(currToken);
            }
        }
        return currToken;
    }
    
    /**
     * @param current token just found to be printed to the output file
     */
    private void printToken(Token current){
        try{
            String name = "tokens.csv";
            File output = new File(name);
            FileWriter fw;
                       
            if(!output.exists()){
                //print header columns first time
                fw = new FileWriter(output);
                fw.write("Token Type|Token Data|\n");
            }
            else{
                fw = new FileWriter(output, true);
            }
            
            fw.write(current.getTokenType().toString());
            //Could do very long if statement testing to see if we have correct 
            //types but if our scanner is working properly this will be fine
            if(current.getTokenData() != null && !(current.getTokenData().equals(""))){
                fw.write("|"+current.getTokenData()+"\n");
            }
            else{
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        }
       catch(IOException e){
           e.printStackTrace();
       }
    }    
}
