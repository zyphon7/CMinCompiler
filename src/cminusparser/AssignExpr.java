/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cmincompiler.CMinusCompiler.globalHash;
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
        Operand src0 = new Operand(OperandType.REGISTER, rhs.getRegNum());
        
        //if in  local, get register and do assign op
        VarExpr var = (VarExpr)lhs;
        Operation op;
        Operand dest;
        if(f.getTable().containsKey(var.getName())){
            Integer regNum = (Integer)f.getTable().get(var.getName());
            op = new Operation(OperationType.ASSIGN, f.getCurrBlock());
            op.setSrcOperand(0, src0);
            dest = new Operand(OperandType.REGISTER, regNum);
            op.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(op);
        }
        //o/w we are working with global var
        else{
           //do store op
           op = new Operation(OperationType.STORE_I,f.getCurrBlock());
           Operand src1 = new Operand(OperandType.STRING, globalHash.get(var.getName()));
           Operand src2 = new Operand(OperandType.INTEGER, 0);
           op.setSrcOperand(0, src0);
           op.setSrcOperand(1, src1);
           op.setSrcOperand(2, src2);
           f.getCurrBlock().appendOper(op);
        }
    }
    
}
