/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;

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
    
    public void genCode(CodeItem i){
        Function f = (Function)i;
        
        //Call genCode on rhs
        rhs.genCode(i);
        //Make this the source operand
        Operand src = new Operand(OperandType.REGISTER, rhs.getRegNum());
        //Call genCode on the lhs
        lhs.genCode(i);
        //Make this the destination operand
        Operand dest = new Operand(OperandType.REGISTER, lhs.getRegNum());
        
        //make op and assign to function
        Operation op = new Operation(OperationType.ASSIGN, f.getCurrBlock());
        f.getCurrBlock().appendOper(op);
    }
    
}
