/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
import CminScanner.Token.TokenType;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;

/**
 *
 * @author Annie
 */
public abstract class Expression {
    
    private static String caller = "EXPRESSION";
    
    static Expression parseExpression(){
        Token oldTok = null;
            if(cminscanner.viewNextToken().getTokenType() == TokenType.ID){
                oldTok = matchToken(TokenType.ID, caller);
                return parseExpressionPrime(oldTok);
            }
            else if(cminscanner.viewNextToken().getTokenType() == TokenType.LP){
                matchToken(TokenType.LP, caller);
                Expression e = parseExpression();
                matchToken(TokenType.RP, caller);
                return parseSimpleExpr(e);
            }
            else if(cminscanner.viewNextToken().getTokenType() == TokenType.NUM){
                oldTok = matchToken(TokenType.NUM, caller);
                return parseSimpleExpr(oldTok);    
            }
            else{
                //error and exit
                return null;
            }
    }
    
    static Expression parseExpressionPrime(Token t){
        return null;
    }
    
    static Expression parseExpressionDoublePrime(){
        return null;
    }
    
    static Expression parseSimpleExpr(Expression e){
        return null;
    }
    
    abstract void print();
    
}
