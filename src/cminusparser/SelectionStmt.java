/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token.TokenType;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import static cminusparser.Expression.parseExpression;
import static cminusparser.Program.INDENT;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
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
public class SelectionStmt extends Statement {
    
    private Expression expr = null;
    private Statement stmt1 = null;
    private Statement stmt2 = null;
    
    private static String caller = "SELECT_STMT";
    
    public SelectionStmt(){
        
    }
    
    static Statement parseSelectionStmt(){
        SelectionStmt s = new SelectionStmt();
        matchToken(TokenType.IF, caller);
        matchToken(TokenType.LP, caller);
        s.expr = parseExpression();
        matchToken(TokenType.RP, caller);
        s.stmt1 = parseStatement();
        
        if(cminscanner.viewNextToken().getTokenType() == TokenType.ELSE){
            matchToken(TokenType.ELSE, caller);
            s.stmt2 = parseStatement();
        }      
        return s;
    }
    
    void print(String s, PrintWriter w){
        w.println(s + "if");
        w.println(s + "(");
        System.out.println(s + "if");
        System.out.println(s + "(");
        expr.print(s+INDENT, w);
        w.println(s + ")");
        System.out.println(s+ ")");
        stmt1.print(s+INDENT, w);
        if(stmt2 != null){
            w.println(s + "else");
            System.out.println(s + "else");
            stmt2.print(s+INDENT, w);
        }
    }
    
    public void genCode(Function f){
        
        //create 2 or 3 blocks
        BasicBlock then1 = new BasicBlock(f);
        BasicBlock else1;
        if(stmt2 != null){
            else1 = new BasicBlock(f);
        }
        else{
            else1 = null;
        }
        BasicBlock post1 = new BasicBlock(f);
        
        //genCode expr
        expr.genCode(f);
        
        //generate branch
        Operation branchOp = new Operation(OperationType.BEQ, f.getCurrBlock());
        Operand src0 = new Operand(OperandType.REGISTER, expr.getRegNum());
        Operand src1 = new Operand(OperandType.INTEGER, 0);
        Operand src2;
        if(else1 != null){
            src2 = new Operand(OperandType.BLOCK, else1.getBlockNum());
        }
        else{
            src2 = new Operand(OperandType.BLOCK, post1.getBlockNum());
        }
        branchOp.setSrcOperand(0, src0);
        branchOp.setSrcOperand(1, src1);
        branchOp.setSrcOperand(2, src2);
        f.getCurrBlock().appendOper(branchOp);
        
        //append then block to the currBlock
        f.appendToCurrentBlock(then1);
        
        //currBlock = THEN
        f.setCurrBlock(then1);
        
        //genCode stmt1
        stmt1.genCode(f);
        
        //append POST
        f.appendToCurrentBlock(post1);
        
        //IF ELSE BLOCK: currBlock = ELSE
        //gencode stmt2 if there
        //JMP to post block
        //append else to unconnected chain
        if(else1 != null){
            f.setCurrBlock(else1);
            stmt2.genCode(f);
            Operation jmpPostOp = new Operation(OperationType.JMP, else1);
            Operand postOp = new Operand(OperandType.BLOCK, post1.getBlockNum());
            jmpPostOp.setSrcOperand(0, postOp);
            f.getCurrBlock().appendOper(jmpPostOp);
            f.appendUnconnectedBlock(else1);  /*uc*/
        }
       
        //currBlock = POST
        f.setCurrBlock(post1);
    }
    
}
