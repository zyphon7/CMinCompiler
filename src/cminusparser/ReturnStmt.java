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
public class ReturnStmt extends Statement{
    
    private Expression expr;
    private static String caller = "RETN_STMT";
    
    public ReturnStmt(){
        
    }
    static Statement parseReturnStmt(){
        ReturnStmt r = new ReturnStmt();
        matchToken(TokenType.RETURN, caller);
        
        if(cminscanner.viewNextToken().getTokenType() == TokenType.ID ||
           cminscanner.viewNextToken().getTokenType() == TokenType.LP ||
           cminscanner.viewNextToken().getTokenType() == TokenType.NUM ){
           //cminscanner.getNextToken();
           r.expr = parseExpression();
        }
        matchToken(TokenType.SEMICOLON, caller);
        return r;
    } 
    
    void print(String s, PrintWriter w){
        w.println(s + "return");
        System.out.println(s + "return");
        if(expr != null){
            expr.print(s+INDENT, w);
        }
        else{
            w.println(";");
            System.out.print(";");
        }
    }
    
    public void genCode(Function f){
            if(expr != null){
                expr.genCode(f);
            
           
            //Adding operation to move expr result into RETREG & add whole
            //operation to the block
            Operation movRet = new Operation(OperationType.ASSIGN, f.getCurrBlock());
            Operand srcOp = new Operand(OperandType.REGISTER, expr.getRegNum());
            Operand destRetReg = new Operand(OperandType.MACRO, "RetReg");
            movRet.setSrcOperand(0, srcOp);
            movRet.setDestOperand(0, destRetReg);
            f.getCurrBlock().appendOper(movRet);
            } 
           //Add jump operation to exit block
           Operation jmpOp = new Operation(OperationType.JMP, f.getCurrBlock());
           Operand srcOp = new Operand(OperandType.BLOCK, f.getReturnBlock().getBlockNum());
           jmpOp.setSrcOperand(0, srcOp);
           f.getCurrBlock().appendOper(jmpOp);
           
           //connect UC if there?
           //f.setCurrBlock(f.getReturnBlock());
            /*if(f.getFirstUnconnectedBlock() != null){
                f.appendToCurrentBlock(f.getFirstUnconnectedBlock());
            }*/
           
    }
}
