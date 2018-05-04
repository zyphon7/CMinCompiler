/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.matchToken;
import static cminusparser.Expression.parseExpression;
import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Annie
 */
public class IterationStmt extends Statement{
    
    private Expression expr;
    private Statement stmt;
    
    private static String caller = "ITER_STMT";
    
    public IterationStmt(){
        
    }
    
    static Statement parseIterationStmt(){
        IterationStmt i = new IterationStmt();
        matchToken(TokenType.WHILE, caller);
        matchToken(TokenType.LP, caller);
        i.expr = parseExpression();
        matchToken(TokenType.RP, caller);
        i.stmt = parseStatement();
        return i;
    }
    
    void print(String s, PrintWriter w){
        w.println(s + "while");
        w.println(s + "(");
        System.out.println(s + "while");
        System.out.println(s + "(");
        expr.print(s+INDENT, w);
        w.println(s + ")");
        System.out.println(s + ")");
        stmt.print(s+INDENT, w);   
    }
    
    public void genCode(Function f){
        BasicBlock whileBlock = new BasicBlock(f);
        BasicBlock bodyBlock = new BasicBlock(f);
        BasicBlock postBlock = new BasicBlock(f);
        
        //gencode whileblock
        expr.genCode(f);
        
        //gen branch
        Operation branchOp = new Operation(Operation.OperationType.BEQ, f.getCurrBlock());
        Operand src0 = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
        Operand src1 = new Operand(Operand.OperandType.INTEGER, 0);
        Operand src2 = new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum());
        branchOp.setSrcOperand(0, src0);
        branchOp.setSrcOperand(1, src1);
        branchOp.setSrcOperand(2, src2);
        f.getCurrBlock().appendOper(branchOp);
        
        //append whileblock to currBlock
        f.appendToCurrentBlock(bodyBlock);
        
        //curr block = body
        f.setCurrBlock(bodyBlock);
        
        //gencode body
        stmt.genCode(f);
        
        //gen whileblock expr again to see if we need to Branch
        expr.genCode(f);
        branchOp = new Operation(Operation.OperationType.BNE, f.getCurrBlock());
        src0 = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
        src1 = new Operand(Operand.OperandType.INTEGER, 0);
        src2 = new Operand(Operand.OperandType.BLOCK, bodyBlock.getBlockNum());
        branchOp.setSrcOperand(0, src0);
        branchOp.setSrcOperand(1, src1);
        branchOp.setSrcOperand(2, src2);
        f.getCurrBlock().appendOper(branchOp);
     
        //append post
        f.appendToCurrentBlock(postBlock);
        
        //currBlock = post
        f.setCurrBlock(postBlock);
    }
    
}
