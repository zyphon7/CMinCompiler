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
            Token oldTok;
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
        if(cminscanner.viewNextToken().getTokenType() == TokenType.EQUAL){
            matchToken(TokenType.EQUAL, caller);
            return parseExpression();
        }
        else if(cminscanner.viewNextToken().getTokenType() == TokenType.LBRACKET){
            matchToken(TokenType.LBRACKET, caller);
            parseExpression();
            matchToken(TokenType.RBRACKET, caller);
            parseExpressionDoublePrime();
        }
        else if(cminscanner.viewNextToken().getTokenType() == TokenType.LP){
            matchToken(TokenType.LP, caller);
            parseArgs();
            matchToken(TokenType.RP, caller);
        }
        else if(cminscanner.viewNextToken().getTokenType() == TokenType.MULTI ||
                cminscanner.viewNextToken().getTokenType() == TokenType.DIVIDE){
            parseSimpleExpr();
        }
        else{
            //error
            return null;
        }
    }
    
    static Expression parseExpressionDoublePrime(){
        if(cminscanner.viewNextToken().getTokenType() == TokenType.EQUAL){
            matchToken(TokenType.EQUAL, caller);
            parseExpression();
        }
        else if(cminscanner.viewNextToken().getTokenType() == TokenType.MULTI ||
                cminscanner.viewNextToken().getTokenType() == TokenType.DIVIDE){
            parseSimpleExpr();
        }
        else{
            return null;
        }
    }
    
    static Expression parseSimpleExpr(Expression e){
        return null;
    }
    
    //parseAEprime through a non null e
    static Expression parseAdditiveExpression(Expression e){
        Expression lhs = parseTerm(e);
        
        while(cminscanner.viewNextToken().getTokenType() == TokenType.PLUS ||
              cminscanner.viewNextToken().getTokenType() == TokenType.MINUS){
            Token oldTok = cminscanner.getNextToken();
            Expression rhs = parseTerm(null);
            lhs = new BinaryExpr(oldTok.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    //parseTermPrime when e isn't null
    static Expression parseTerm(Expression e){
        Expression lhs = e;
        
        if(e == null){
            lhs = parseFactor();
        }

        while(cminscanner.viewNextToken().getTokenType() == TokenType.MULTI ||
              cminscanner.viewNextToken().getTokenType() == TokenType.DIVIDE){
            Token oldTok = cminscanner.getNextToken();
            Expression rhs = parseFactor();
            lhs = new BinaryExpr(oldTok.getTokenType(), lhs, rhs);
        }
        return lhs;
    }
    
    static Expression parseFactor(){
        Token currToken = cminscanner.viewNextToken();
        switch(currToken.getTokenType()){
            case LP:
                matchToken(TokenType.LP, caller);
                Expression expr = parseExpression();
                matchToken(TokenType.RP, caller);
                return expr;
            case ID:
                matchToken(TokenType.ID, caller);
                return parseVarCall(currToken); //labeled as create IdentExpr
            case NUM:
                matchToken(TokenType.NUM, caller);
                return new NumExpr((int)currToken.getTokenData());
            default:
                //error
                return null;
        }
    }
    
    static Expression parseVarCall(Token t){
        return null;
    }
    
    abstract void print();
    
}
