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
                //Turn ID into a varexpr
                Expression id = new VarExpr(oldTok.getTokenData().toString(), null);
                return parseExpressionPrime(id);
            }
            else if(cminscanner.viewNextToken().getTokenType() == TokenType.LP){
                matchToken(TokenType.LP, caller);
                Expression e = parseExpression();
                matchToken(TokenType.RP, caller);
                return parseSimpleExpr(e);
            }
            else if(cminscanner.viewNextToken().getTokenType() == TokenType.NUM){
                Expression num = new NumExpr((int)cminscanner.viewNextToken().getTokenData());
                matchToken(TokenType.NUM, caller);
                return parseSimpleExpr(num);    
            }
            else{
                //error and exit
                return null;
            }
    }
    
    static Expression parseExpressionPrime(Expression lhs){
        TokenType t = cminscanner.viewNextToken().getTokenType();
        if(t == TokenType.EQUAL){
            matchToken(TokenType.EQUAL, caller);
            Expression e = parseExpression();
            return new AssignExpr(lhs, e);
        }
        else if(t == TokenType.LBRACKET){
            matchToken(TokenType.LBRACKET, caller);
            Expression index = parseExpression();
            matchToken(TokenType.RBRACKET, caller);
            ((VarExpr)lhs).setIndex(index);
            //AssignExpr a = new AssignExpr(t.getTokenData().toString(), e);
            return parseExpressionDoublePrime(lhs);
        }
        else if(t == TokenType.MULTI || t == TokenType.DIVIDE ||
                t == TokenType.PLUS || t == TokenType.MINUS ||
                t == TokenType.GREATEREQ || t == TokenType.GREATER ||
                t == TokenType.LESS || t == TokenType.LESSEQ ||
                t == TokenType.DOUBLEEQUAL || t == TokenType.NOTEQUAL ||
                t == TokenType.RP || t == TokenType.RBRACKET ||
                t == TokenType.COMMA || t == TokenType.SEMICOLON){
            return parseSimpleExpr(lhs); 
        }
        else if(t == TokenType.LP){
            matchToken(TokenType.LP, caller);
            Expression args = parseArgs(lhs); //Correct?
            matchToken(TokenType.RP, caller);
            if(t == TokenType.MULTI || t == TokenType.DIVIDE ||
                t == TokenType.PLUS || t == TokenType.MINUS ||
                t == TokenType.GREATEREQ || t == TokenType.GREATER ||
                t == TokenType.LESS || t == TokenType.LESSEQ ||
                t == TokenType.DOUBLEEQUAL || t == TokenType.NOTEQUAL ||
                t == TokenType.RP || t == TokenType.RBRACKET ||
                t == TokenType.COMMA || t == TokenType.SEMICOLON){
                Expression e = parseSimpleExpr(args);
                return e;
            }
            else{
                return args;
            }
            
        }
        else{
            //error
            return null;
        }
    }
    
    static Expression parseExpressionDoublePrime(Expression lhs){
        TokenType t = cminscanner.viewNextToken().getTokenType();
        if(t == TokenType.EQUAL){
            matchToken(TokenType.EQUAL, caller);
            AssignExpr a = new AssignExpr(lhs, parseExpression());
            return a;
        }
        else if(t == TokenType.MULTI || t == TokenType.DIVIDE ||
                t == TokenType.PLUS || t == TokenType.MINUS ||
                t == TokenType.GREATEREQ || t == TokenType.GREATER ||
                t == TokenType.LESS || t == TokenType.LESSEQ ||
                t == TokenType.DOUBLEEQUAL || t == TokenType.NOTEQUAL ||
                t == TokenType.RP || t == TokenType.RBRACKET ||
                t == TokenType.COMMA || t == TokenType.SEMICOLON){
            return parseSimpleExpr(lhs);
        }
        else{
            return null;
        }
    }
    
    static Expression parseSimpleExpr(Expression e){
        TokenType t = cminscanner.viewNextToken().getTokenType();
        Expression lhs = parseAdditiveExpression(e);
        
        while(t == TokenType.LESSEQ || t == TokenType.LESS ||
           t == TokenType.GREATER || t == TokenType.GREATEREQ ||
           t == TokenType.DOUBLEEQUAL || t == TokenType.NOTEQUAL){
            matchToken(t, caller);
            Expression rhs = parseAdditiveExpression(null);
            lhs = new BinaryExpr(t, lhs, rhs);
            t = cminscanner.viewNextToken().getTokenType();
        }
        return lhs;
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
    
    static Expression parseArgs(Expression lhs){
        //create callexpr here?
    }
    
    abstract void print();
    
}
