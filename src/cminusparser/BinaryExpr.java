/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
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
public class BinaryExpr extends Expression {
    
    private TokenType token;
    private Expression expr1;
    private Expression expr2;
    private static final String caller = "BinaryExpr";
    
    public BinaryExpr(TokenType t, Expression e1, Expression e2){
        token = t;
        expr1 = e1;
        expr2 = e2;
    }
    
    void print(String s, PrintWriter w){
        String oper;
        switch(token){
            case GREATER:
                oper = ">";
                break;
            case GREATEREQ:
                oper = ">=";
                break;
            case LESS:
                oper = "<";
                break;
            case LESSEQ:
                oper = "<=";
                break;
            case DOUBLEEQUAL:
                oper = "==";
                break;
            case NOTEQUAL:
                oper = "!=";
                break;
            case PLUS:
                oper = "+";
                break;
            case MINUS:
                oper = "-";
                break;
            case MULTI:
                oper = "*";
                break;
            case DIVIDE:
                oper = "/";
                break;
            default:
                oper = "oper";
                break;
        }
        System.out.println(s + oper);
        w.println(s + oper);
        expr1.print(s+INDENT, w);
        expr2.print(s+INDENT, w);
    }
    
    public void genCode(CodeItem i){
        Function f = (Function)i;
        expr1.genCode(i);
        expr2.genCode(i);
        OperationType operType;
        switch(token){
            case GREATER:
                operType = OperationType.GT;
                break;
            case LESS:
                operType = OperationType.LT;
                break;
            case GREATEREQ:
                operType = OperationType.GTE;
                break;
            case LESSEQ:
                operType = OperationType.LTE;
                break;
            case DOUBLEEQUAL:
                operType = OperationType.EQUAL;
                break;
            case NOTEQUAL:
                operType = OperationType.NOT_EQUAL;
                break;
            default:
                System.out.println(caller + " WOOPS in OperationType!");
                operType = null;
                break;
        }

        Operation binOper = new Operation(operType, f.getCurrBlock());
        
        //make operands & append operation to basic block
        Operand srcOper1 = new Operand(OperandType.REGISTER, expr1.getRegNum());
        binOper.setSrcOperand(0,srcOper1);
        Operand srcOper2 = new Operand(OperandType.REGISTER, expr2.getRegNum());
        binOper.setSrcOperand(1, srcOper2);
        Operand destOper = new Operand(OperandType.REGISTER, Expression.getNextRegNum());
        binOper.setDestOperand(2, destOper);
        f.getCurrBlock().appendOper(binOper);
        
    }
    
}
