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
public class ReturnStmt extends Statement{
    
    private Expression expr;
    private static String caller = "RETN_STMT";
    
    public ReturnStmt(){
        
    }
    static Statement parseReturnStmt(){
        ReturnStmt r = new ReturnStmt();
        matchToken(TokenType.RETURN, caller);
        
        if(cminscanner.viewNextToken().getTokenType() == TokenType.ID ||
           cminscanner.viewNextToken().getTokenType() == TokenType.LP ||
           cminscanner.viewNextToken().getTokenType() == TokenType.NUM ){
           //cminscanner.getNextToken();
           r.expr = parseExpression();
        }
        matchToken(TokenType.SEMICOLON, caller);
        return r;
    } 
    
    void print(String s, PrintWriter w){
        w.println(s + "return");
        System.out.println(s + "return");
        if(expr != null){
            expr.print(s+INDENT, w);
        }
        else{
            w.println(";");
            System.out.print(";");
        }
    }
    
    public void genCode(CodeItem i){

    }
}
