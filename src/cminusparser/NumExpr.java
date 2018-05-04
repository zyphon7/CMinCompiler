/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import java.io.PrintWriter;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;

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
    
    public void genCode(Function f){
        this.setRegNum(f.getNewRegNum());
        Operation op = new Operation(OperationType.ASSIGN, f.getCurrBlock());
        Operand src = new Operand(OperandType.INTEGER, num);
        Operand dest = new Operand(OperandType.REGISTER, this.getRegNum());
        op.setSrcOperand(0, src);
        op.setDestOperand(0, dest);
        f.getCurrBlock().appendOper(op);
    }
}
