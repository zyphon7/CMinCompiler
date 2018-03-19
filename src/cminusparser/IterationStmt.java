/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.matchToken;
import static cminusparser.Expression.parseExpression;

/**
 *
 * @author Annie
 */
public class IterationStmt extends Statement{
    
    private Expression expr;
    private Statement stmt;
    
    private static String caller = "ITER_STMT";
    
    public IterationStmt(){
        
    }
    
    static Statement parseIterationStmt(){
        IterationStmt i = new IterationStmt();
        matchToken(TokenType.WHILE, caller);
        matchToken(TokenType.LP, caller);
        i.expr = parseExpression();
        matchToken(TokenType.RP, caller);
        i.stmt = parseStatement();
        return i;
    }
    
    void print(){
        
    }
    
}
