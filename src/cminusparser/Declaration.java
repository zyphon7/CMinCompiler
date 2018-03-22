/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import cminusparser.FunDecl.FunType;
import static cminusparser.FunDecl.parseFunDecl;
import static cminusparser.VarDecl.parseVarDecl;

/**
 *
 * @author Annie and Spencer
 */
public abstract class Declaration {
    private static String caller = "DECLARATION";
    
    static Declaration parseDeclaration(Token e){
        Token ID = new Token();
        if(e == null){
            switch(cminscanner.viewNextToken().getTokenType()){
             case INT:
                 matchToken(Token.TokenType.INT, caller);
                 ID = matchToken(Token.TokenType.ID, caller);
                 //VarExpr id = new VarExpr(ID.getTokenData().toString(), null);
                 return parseDeclaration(ID); //decl'
             case VOID:
                 matchToken(Token.TokenType.VOID, caller);
                 ID = matchToken(Token.TokenType.ID, caller);
                 Declaration f = parseFunDecl(FunType.VOID, ID);
                 return f;
             default:
                 //log error and exit
                 break;
            }
        }
        else{
             if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.SEMICOLON
                     || cminscanner.viewNextToken().getTokenType() == Token.TokenType.LBRACKET){
                return parseVarDecl(e);
             }
             else if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.LP){
                return parseFunDecl(FunType.INT, e);
             }
             else{
                //Add function to log error and exit hah :P :P :P
             }
        }
        return null;
        
    }
    abstract void print();
}
