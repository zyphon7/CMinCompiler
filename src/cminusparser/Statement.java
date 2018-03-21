/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CompoundStmt.parseCompoundStmt;
import static cminusparser.ExpressionStmt.parseExpressionStmt;
import static cminusparser.IterationStmt.parseIterationStmt;
import static cminusparser.ReturnStmt.parseReturnStmt;
import static cminusparser.SelectionStmt.parseSelectionStmt;

/**
 *
 * @author Annie
 */
public abstract class Statement {
    
    static Statement parseStatement(){
        TokenType t = cminscanner.viewNextToken().getTokenType();
        //expression-stmt
        if(t == TokenType.ID || t == TokenType.LP || t == TokenType.NUM ||
                t == TokenType.SEMICOLON){
            return parseExpressionStmt();
        }
        else if(t == TokenType.LCURLY){
            return parseCompoundStmt();
        }
        else if(t == TokenType.IF){
            return parseSelectionStmt();
        }
        else if(t == TokenType.WHILE){
            return parseIterationStmt();
        }
        else if(t == TokenType.RETURN){
            return parseReturnStmt();
        }
        else{
            //error message
            return null;
        }
    }
    abstract void print();
    
}
