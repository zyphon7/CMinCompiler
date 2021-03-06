/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.Program.INDENT;
import java.io.PrintWriter;

/**
 *
 * @author Annie
 */
public class AssignExpr extends Expression {
    
    Expression lhs;
    Expression rhs;
    
    public AssignExpr(){ }
    
    public AssignExpr(Expression l, Expression r){
        lhs = l;
        rhs = r;
    }
    
    void print(String s, PrintWriter w){
        System.out.println(s + INDENT + "=");
        w.println(s + INDENT + "=");
        lhs.print(s + INDENT + INDENT, w);
        rhs.print(s + INDENT + INDENT, w);
    }
    
}
