/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import java.util.ArrayList;
import lowlevel.Attribute;
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
    
    public void genCode(Function f){
        
        //iterate over args
        for(int j = 0; j < args.size(); j++){
            
            Expression e = args.get(j);
            e.genCode(f);
            //create operand add to operation and append to block
            Operation passOp = new Operation(OperationType.PASS,f.getCurrBlock());
            passOp.attr = new Attribute("PARAM_NUM", new String(Integer.toString(j)));
            Operand srcOper0 = new Operand(OperandType.REGISTER, e.getRegNum());
            passOp.setSrcOperand(0,srcOper0);
            f.getCurrBlock().appendOper(passOp);
        }
       
        //add call operation & annotate with args.size()
        Operation callOp = new Operation(OperationType.CALL, f.getCurrBlock());
        String name = ((VarExpr)ID).getName();
        Operand srcOper0 =  new Operand(OperandType.STRING, name);
        callOp.setSrcOperand(0, srcOper0);
        callOp.attr = new Attribute("numParams", new String(Integer.toString(args.size())));
        f.getCurrBlock().appendOper(callOp);
        
        //move RetReg into regular register
        Operation movOp = new Operation(OperationType.ASSIGN, f.getCurrBlock());
        Operand srcOp0 = new Operand(OperandType.MACRO, "RetReg");
        movOp.setSrcOperand(0, srcOp0);
        this.setRegNum(f.getNewRegNum());
        Operand destOp0 = new Operand(OperandType.REGISTER, this.getRegNum());
        movOp.setDestOperand(0, destOp0);
        f.getCurrBlock().appendOper(movOp);
    }
    
}
