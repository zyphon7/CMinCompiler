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
public class VarDecl extends Declaration {
    
    private String name;
    private int num;
    
    public VarDecl(String n, int i){
        name = n;
        num = i;
    }
    
    //TODO
    static Declaration parseVarDecl(){
        return null;
    }
    
    void print(){
        
    }
    
}
