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
import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.CodeItem;

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
            matchToken(TokenType.ELSE, caller);
            s.stmt2 = parseStatement();
        }      
        return s;
    }
    
    void print(String s, PrintWriter w){
        w.println(s + "if");
        w.println(s + "(");
        System.out.println(s + "if");
        System.out.println(s + "(");
        expr.print(s+INDENT, w);
        w.println(s + ")");
        System.out.println(s+ ")");
        stmt1.print(s+INDENT, w);
        if(stmt2 != null){
            w.println(s + "else");
            System.out.println(s + "else");
            stmt2.print(s+INDENT, w);
        }
    }
    
    public void genCode(CodeItem i){
        
    }
    
}
