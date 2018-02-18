/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2015  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* Java 1.2 language lexer specification */

/* Use together with unicode.flex for Unicode preprocesssing */
/* and java12.cup for a Java 1.2 parser                      */

/* Note that this lexer specification is not tuned for speed.
   It is in fact quite slow on integer and floating point literals, 
   because the input is read twice and the methods used to parse
   the numbers are not very fast. 
   For a production quality application (e.g. a Java compiler) 
   this could be optimized */

package CminScanner;

import CminScanner.Token.TokenType;
import CminScanner.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

%%

%public
%class CminusScannerB
%implements Scanner

%type Token

%unicode

%line
%column

%{
  StringBuilder string = new StringBuilder();
    private Token nextToken;
    private int lineLength;
    
     public static void main(String[] args) {
        try{
            File cminFile = new File("CminPrograms/program2.txt");
            CminusScannerB cminscanner = new CminusScannerB(new BufferedReader(new FileReader(cminFile)));
            
            while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                cminscanner.getNextToken();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }       
    }

    
   public Token viewNextToken(){
        return nextToken;
    }

   public Token getNextToken(){
        Token returnToken = nextToken;
        printToken(nextToken);
        if(nextToken.getTokenType()!= TokenType.EOF){
            try{
                nextToken = yylex();
               
            }
            catch(IOException i){
                i.printStackTrace();
            }
        }
        return returnToken;
    }

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

  /** 
   * assumes correct representation of a long value for 
   * specified radix in scanner buffer from <code>start</code> 
   * to <code>end</code> 
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }
%}
%init{
        this.zzReader = in;
        lineLength = -1;
        String name = "tokens.csv";
        File output = new File(name);
        //Delete the file everytime scanner is run
        if(output.exists()){
            output.delete();
        }
        try{
            nextToken = yylex();
        }
        catch(IOException i){
            i.printStackTrace();
        }
%init}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment}

TraditionalComment = "/*"

/* identifiers */
Identifier = [:jletter:][:jletter:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state COMMENT

%%

<YYINITIAL> {

  /* keywords */
  "else"                         { return new Token(Token.TokenType.ELSE); }
  "int"                          { return new Token(Token.TokenType.INT); }
  "if"                           { return new Token(Token.TokenType.IF); }
  "return"                       { return new Token(Token.TokenType.RETURN); }
  "void"                         { return new Token(Token.TokenType.VOID); }
  "while"                        { return new Token(Token.TokenType.WHILE); }
  
  
  /* separators */
  "("                            { return new Token(Token.TokenType.LP); }
  ")"                            { return new Token(Token.TokenType.RP); }
  "{"                            { return new Token(Token.TokenType.LCURLY); }
  "}"                            { return new Token(Token.TokenType.RCURLY); }
  "["                            { return new Token(Token.TokenType.LBRACKET); }
  "]"                            { return new Token(Token.TokenType.RBRACKET); }
  ";"                            { return new Token(Token.TokenType.SEMICOLON); }
  ","                            { return new Token(Token.TokenType.COMMA); }
  
  /* operators */
  "="                            { return new Token(Token.TokenType.EQUAL); }
  ">"                            { return new Token(Token.TokenType.GREATER); }
  "<"                            { return new Token(Token.TokenType.LESS); }
  "=="                           { return new Token(Token.TokenType.DOUBLEEQUAL); }
  "<="                           { return new Token(Token.TokenType.LESSEQ); }
  ">="                           { return new Token(Token.TokenType.GREATEREQ); }
  "!="                           { return new Token(Token.TokenType.NOTEQUAL); }
  "+"                            { return new Token(Token.TokenType.PLUS); }
  "-"                            { return new Token(Token.TokenType.MINUS); }
  "*"                            { return new Token(Token.TokenType.MULTI); }
  "/"                            { return new Token(Token.TokenType.DIVIDE); }
       
  

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */
  "-2147483648"                  { return new Token(Token.TokenType.INT, new Integer(Integer.MIN_VALUE)); }
  
  {DecIntegerLiteral}            { return new Token(Token.TokenType.INT, new Integer(yytext())); }
 
  /* comments */
  {Comment}                      { yybegin(COMMENT); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return new Token(Token.TokenType.ID, yytext()); }  
}

<COMMENT> {
"*/"                         { yybegin(YYINITIAL); }
[^]                          { /* ignore */ }
  <<EOF>>                         { throw new RuntimeException("File ended in the middle of a comment."); } 
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return new Token(Token.TokenType.EOF); }