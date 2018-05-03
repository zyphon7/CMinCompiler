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
    
    public void genCode(Function f){
        
        //Call genCode on rhs
        rhs.genCode(f);
        if(rhs.getRegNum() == 0){
            rhs.setRegNum(getCurrRegNum());
        }
        
        //Make this the source operand
        Operand src = new Operand(OperandType.REGISTER, rhs.getRegNum());
        
        //if in  local, get register and do assign op
        VarExpr var = (VarExpr)lhs;
        Operation op;
        Operand dest;
        if(f.getTable().containsKey(var)){
            Integer regNum = (Integer)f.getTable().get(var);
            op = new Operation(OperationType.ASSIGN, f.getCurrBlock());
            op.setSrcOperand(0, src);
            dest = new Operand(OperandType.REGISTER, regNum);
            op.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(op);
        }
        //o/w we are working with global var
        else{
           //do store op 
        }
    }
    
}
