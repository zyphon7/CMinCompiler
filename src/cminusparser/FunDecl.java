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
import static cminusparser.Program.INDENT;
import java.util.ArrayList;

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
    void print(String s){
        String paramString = stringParams(params);
        if(cmpdstmt == null){
            System.out.println(s + type.toString() + " " + name + " " + paramString + ";");
        }
        else{
            System.out.println(s + type.toString() + " " + name + " " + paramString);
            cmpdstmt.print(s);
        }
    }
    
    public enum FunType { INT, VOID }
}
