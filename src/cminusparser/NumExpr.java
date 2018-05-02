/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Operand;
import static lowlevel.Operand.OperandType.REGISTER;

/**
 *
 * @author Annie
 */
public class NumExpr extends Expression{
    
    private int num;
    
    public NumExpr(int n){
        num = n;
    }
    
    void print(String s, PrintWriter w){
        w.println(s + Integer.toString(num));
        System.out.println(s + Integer.toString(num));
    }
    
    public void genCode(CodeItem i){
        this.setRegNum(this.getNextRegNum());
    }
}
