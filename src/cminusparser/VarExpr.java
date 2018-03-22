/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import cminusparser.FunDecl.FunType;

/**
 *
 * @author Annie
 */
public class VarExpr extends Expression{
    private String name;
    private Expression index;
    
    public VarExpr(){ }
    
    //will pass null if no array
    public VarExpr(String n, Expression i){
        name = n;
        index = i;
    }
    
    public void setIndex(Expression e) {
        index = e;
    }
    
    public String getName(){
        return name;
    }
    
    void print(){
        
    }
}
