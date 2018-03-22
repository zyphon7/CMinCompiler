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
import static cminusparser.VarDecl.parseVarDecl;
import java.util.ArrayList;

/**
 *
 * @author Annie
 */
public class CompoundStmt extends Statement{
    
    private ArrayList<Declaration> varDecls = new ArrayList<Declaration>();
    private ArrayList<Statement> stmtList = new ArrayList<Statement>();
    
    private static final String caller = "CompoundStmt";
    
    public CompoundStmt(){
        
    }
    
    static Statement parseCompoundStmt(){
        CompoundStmt c = new CompoundStmt();
        matchToken(TokenType.LCURLY, caller);
        while(cminscanner.viewNextToken().getTokenType() == TokenType.INT){
            c.varDecls.add(parseVarDecl(null));
        }
        //statement
        while(cminscanner.viewNextToken().getTokenType() == TokenType.ID ||
              cminscanner.viewNextToken().getTokenType() == TokenType.LP ||
              cminscanner.viewNextToken().getTokenType() == TokenType.NUM ||
              cminscanner.viewNextToken().getTokenType() == TokenType.SEMICOLON ||
              cminscanner.viewNextToken().getTokenType() == TokenType.IF ||
              cminscanner.viewNextToken().getTokenType() == TokenType.WHILE ||
              cminscanner.viewNextToken().getTokenType() == TokenType.RETURN ||
              cminscanner.viewNextToken().getTokenType() == TokenType.LCURLY){
            
            c.stmtList.add(parseStatement());
        }
        matchToken(TokenType.RCURLY, caller);
        return c;
    }
    
    void print(){
        
    }
    
}
