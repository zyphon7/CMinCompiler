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
import static cminusparser.Expression.parseExpression;

/**
 *
 * @author Annie
 */
public class SelectionStmt extends Statement {
    
    private Expression expr = null;
    private Statement stmt1 = null;
    private Statement stmt2 = null;
    
    private static String caller = "SELECT_STMT";
    
    public SelectionStmt(){
        
    }
    
    static Statement parseSelectionStmt(){
        SelectionStmt s = new SelectionStmt();
        matchToken(TokenType.IF, caller);
        matchToken(TokenType.LP, caller);
        s.expr = parseExpression();
        matchToken(TokenType.RP, caller);
        s.stmt1 = parseStatement();
        
        if(cminscanner.viewNextToken().getTokenType() == TokenType.ELSE){
            cminscanner.getNextToken();
            s.stmt2 = parseStatement();
        }      
        return s;
    }
    
    void print(){
        
    }
    
}