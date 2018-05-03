/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cmincompiler.CMinusCompiler.globalHash;
import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import java.util.HashMap;
import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;

/**
 *
 * @author Annie
 */
public class VarExpr extends Expression{
    private String name;
    private Expression index;
    private static String caller = "VAR_EXPR";
    
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
        
        Function f = (Function)i;
        HashMap localTable = f.getTable();
        Operation op;
        
        //if in local, load reg num
        if(localTable.containsKey(name)){
            Integer reg = (Integer)localTable.get(name);
            if(reg != null){
                this.setRegNum(reg);
            }
            else{
                System.out.println(caller + "REGValue wasn't in local symbol table.");
            }
        }
        //else in global create a load oper
        else{
            String value = (String)globalHash.get(name);
            op = new Operation(Operation.OperationType.LOAD_I, f.getCurrBlock());
            Operand src = new Operand(OperandType.STRING, name);
            Operand dest = new Operand(OperandType.REGISTER, Expression.getNextRegNum());
            op.setSrcOperand(0, src);
            op.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(op);
        }
    }
}
