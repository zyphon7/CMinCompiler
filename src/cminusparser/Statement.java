/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.ExpressionStmt.parseExpressionStmt;

/**
 *
 * @author Annie
 */
public abstract class Statement {
    
    static Statement parseStatement(){
        Statement s = null;
        TokenType t = cminscanner.viewNextToken().getTokenType();
        //expression-stmt
        if(t == TokenType.ID || t == TokenType.LP || t == TokenType.NUM ||
                t == TokenType.SEMICOLON){
            s = parseExpressionStmt();
        }
    }
    abstract void print();
    
}
