/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;
import java.util.ArrayList;

/**
 *
 * @author Annie
 */
public class FunDecl extends Declaration{
    
    private funtype type;
    private String name;
    private ArrayList<Parameter> params = new ArrayList<Parameter>();
    private CompoundStmt cmpdstmt;
    
    public FunDecl(funtype t, String n, CompoundStmt c){
        type = t;
        name = n;
        cmpdstmt = c;
    }
    
    //TODO
    static Declaration parseFunDecl(){
        return null;
    }
    
    //TODO
    void print(){
        
    }
    
    public enum funtype { INT, VOID }
}
