/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import java.io.PrintWriter;

/**
 *
 * @author Annie and Spencer
 */
public class VarDecl extends Declaration {
    
    private String name;
    private Integer num;
    private static String caller = "VARDECL";
    
    public VarDecl(){}
    
    public VarDecl(String n, Integer i){
        name = n;
        num = i;
    }
    
    static Declaration parseVarDecl(Token id){
            VarDecl var = new VarDecl();
            if(id == null){
                matchToken(Token.TokenType.INT, caller);
                var.name = matchToken(Token.TokenType.ID, caller).getTokenData().toString();
            }
            else{
                var.name = id.getTokenData().toString();
            }
            return parseBracketNum(var);
     }
    
    static Declaration parseBracketNum(VarDecl v){
            if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.LBRACKET){
                matchToken(Token.TokenType.LBRACKET, caller);
                v.num = Integer.parseInt((String)matchToken(Token.TokenType.NUM, caller).getTokenData());
                matchToken(Token.TokenType.RBRACKET, caller);
                matchToken(Token.TokenType.SEMICOLON, caller);
            }
            else if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.SEMICOLON){
                v.num = null;
                matchToken(Token.TokenType.SEMICOLON, caller);
            }
            else {
                //log error and die
            }
        return v;
    }
    
    void print(String s, PrintWriter w){
        if(num != null){
            w.println(s + "int " + name + "[" + Integer.toString(num) + "];");
            System.out.println(s + "int " + name + "[" + Integer.toString(num) + "];");
        }
        else{
         w.println(s + "int " + name + ";");
         System.out.println(s + "int " + name + ";");   
        }
    }
    
}
