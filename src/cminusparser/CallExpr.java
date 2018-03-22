/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

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
     
    void print(String s){
        
    }
    
}
