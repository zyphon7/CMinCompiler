/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;
import CminScanner.Token;
import static cminusparser.CminParser.matchToken;
import static cminusparser.CompoundStmt.parseCompoundStmt;
import static cminusparser.Parameter.parseParams;
import static cminusparser.Parameter.stringParams;
import java.io.PrintWriter;
import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;

/**
 *
 * @author Annie and Spencer
 */
public class FunDecl extends Declaration{
    
    private static String caller = "FUNDECL";
    private FunType type;
    private String name;
    private ArrayList<Parameter> params = new ArrayList<Parameter>();
    private Statement cmpdstmt;
    
    public FunDecl(){
        
    }
    public FunDecl(FunType t, String n, Statement c){
        type = t;
        name = n;
        cmpdstmt = c;
    }
    
    //TODO
    static Declaration parseFunDecl(FunType t, Token id){
        FunDecl f = new FunDecl();
        f.name = id.getTokenData().toString();
        f.type = t;
        matchToken(Token.TokenType.LP, caller);
        f.params = parseParams();
        matchToken(Token.TokenType.RP, caller);
        f.cmpdstmt = parseCompoundStmt();
        return f;
    }
    
    //TODO
    void print(String s, PrintWriter w){
        String paramString = stringParams(params);
        if(cmpdstmt == null){
            w.println(s + type.toString().toLowerCase() + " " + name + " " + paramString + ";");
            System.out.println(s + type.toString().toLowerCase() + " " + name + " " + paramString + ";");
        }
        else{
            w.println(s + type.toString().toLowerCase() + " " + name + " " + paramString);
            System.out.println(s + type.toString().toLowerCase() + " " + name + " " + paramString);
            cmpdstmt.print(s, w);
        }
    }
    
    public CodeItem genCode(){
        
        //create a new function
        int type;
        if(this.type == FunType.INT){
            type = Data.TYPE_INT;
        }
        else{
            type = Data.TYPE_VOID;
        }
        Function func = new Function(type, this.name); 
        
        //create new block & make block currBlock
        //FUNC_ENTRY
        func.createBlock0();
        func.setCurrBlock(func.getFirstBlock());
        BasicBlock body = new BasicBlock(func);
        func.appendToCurrentBlock(body);
        func.setCurrBlock(body);
        
        //make LL of funcparams
        //POTENTIAL PROBLEM
        //options:
        //func(void)
        //func (int i)
        //func(int a, int b...)
        FuncParam firstParam;
        Parameter p = params.get(0);
        if(p.getFunType() == FunType.VOID){
            firstParam = null;
        }
        
        else{
            firstParam = new FuncParam(p.getType(), p.getName());
        }
        
        for(int i = 1; i < params.size(); i++){
            p = params.get(i);
            FuncParam nextParam = new FuncParam(p.getType(), p.getName());
            firstParam.setNextParam(nextParam);
        }
        
        //assign the list of funcparams to the current function
        func.setFirstParam(firstParam);
        
        //call gen code on cmpdstmt (pass the func down)
        cmpdstmt.genCode(func);
        
        //append return block
        func.appendToCurrentBlock(func.getReturnBlock());
        func.setCurrBlock(func.getReturnBlock());
            if(func.getFirstUnconnectedBlock() != null){
                func.appendToCurrentBlock(func.getFirstUnconnectedBlock());
            }
        //return it back up (with the block inside)
        return func;
    }
    
    public enum FunType { INT, VOID }
}
