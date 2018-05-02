/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.CodeItem;

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
    
    void print(String s, PrintWriter w){
        if(index == null){
            w.println(s + name);
            System.out.println(s + name);
        }
        else{
            w.println(s + name + "[");
            System.out.println(s + name + "[");
            index.print(s+INDENT, w);
            System.out.println(s + "]");
            w.println(s + "]");
        }
    }
    
    public void genCode(CodeItem i){
        //look up location in symbol table
        //if in global create a load oper
            //append new oper to f.getCurrBlock()
        
    }
}
