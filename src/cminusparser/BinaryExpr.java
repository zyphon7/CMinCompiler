/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.Program.INDENT;

/**
 *
 * @author Annie
 */
public class BinaryExpr extends Expression {
    
    private TokenType token;
    private Expression expr1;
    private Expression expr2;
    private static final String caller = "BinaryExpr";
    
    public BinaryExpr(TokenType t, Expression e1, Expression e2){
        token = t;
        expr1 = e1;
        expr2 = e2;
    }
    
    void print(String s){
        System.out.println(s + token.toString());
        expr1.print(s+INDENT);
        expr2.print(s+INDENT);
    }
    
}
