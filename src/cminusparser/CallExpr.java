/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Annie
 */
public class CallExpr extends Expression{
    
    private Expression ID;
    private ArrayList<Expression> args = new ArrayList<Expression>();
    
     public CallExpr(Expression id){
         ID = id;
     }
     
     public void addArgument(Expression e){
         args.add(e);
     }
     
    void print(String s, PrintWriter w){
        ID.print(s, w);
        w.println(s + "(");
        System.out.println(s + "(");
        for(int i = 0; i < args.size(); i++){
            args.get(i).print(s+INDENT, w);
        }
        w.println(s + ")");
        System.out.println(s + ")");
    }
    
}
