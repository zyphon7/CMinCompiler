/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import static cminusparser.Expression.parseExpression;

/**
 *
 * @author Annie
 */
public class ExpressionStmt extends Statement{
    
    private Expression expr;
    private static final String caller = "EXPR_STMT";
    
    public ExpressionStmt(){
        
    }
    
    static Statement parseExpressionStmt(){
        ExpressionStmt e = new ExpressionStmt();
        if(cminscanner.viewNextToken().getTokenType() == TokenType.ID ||
           cminscanner.viewNextToken().getTokenType() == TokenType.LP ||
           cminscanner.viewNextToken().getTokenType() == TokenType.NUM){
           e.expr = parseExpression();
        }
        matchToken(TokenType.SEMICOLON, caller);
        return e;
    }
    
    void print(){
        
    }
    
}
