/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.CodeItem;

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
    
    void print(String s, PrintWriter w){
        String oper;
        switch(token){
            case GREATER:
                oper = ">";
                break;
            case GREATEREQ:
                oper = ">=";
                break;
            case LESS:
                oper = "<";
                break;
            case LESSEQ:
                oper = "<=";
                break;
            case DOUBLEEQUAL:
                oper = "==";
                break;
            case NOTEQUAL:
                oper = "!=";
                break;
            case PLUS:
                oper = "+";
                break;
            case MINUS:
                oper = "-";
                break;
            case MULTI:
                oper = "*";
                break;
            case DIVIDE:
                oper = "/";
                break;
            default:
                oper = "oper";
                break;
        }
        System.out.println(s + oper);
        w.println(s + oper);
        expr1.print(s+INDENT, w);
        expr2.print(s+INDENT, w);
    }
    
    public void genCode(){
        
    }
    
}
