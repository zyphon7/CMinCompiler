/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
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
    
    public IterationStmt(){
        
    }
    
    static IterationStmt parseIterationStmt(){
        IterationStmt i = new IterationStmt();
        boolean matched = matchToken(TokenType.WHILE);
        if(!matched){
            System.out.println("No match for while.");
            System.exit(1);
        }
        matched = matchToken(TokenType.LP);
        if(!matched){
           System.out.println("No match for LP.");
           System.exit(1);
        }
        i.expr = parseExpression();
        matched = matchToken(TokenType.RP);
        if(!matched){
            System.out.println("No match for RP.");
            System.exit(1);
        }
        i.stmt = parseStatement();
        return i;
    }
    
    void print(){
        
    }
    
}
