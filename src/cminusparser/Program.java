/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.CminParser.program;
import static cminusparser.Declaration.parseDeclaration;
import java.util.ArrayList;

/**
 *
 * @author Annie
 */
public class Program {
    
    private ArrayList<Declaration> decls = new ArrayList<Declaration>();
    
    public Program(){
        
    }
    
    void addDecl(Declaration d){
        decls.add(d);
    }
    
    public int declCount(){
        return decls.size();
    }
    
    public Declaration getDecl(int index){
        return decls.get(index);
    }
    
    protected void printProgram(){
        System.out.println("PROGRAM {");
        for(int i = 0; i < program.declCount(); i++){
            program.getDecl(i).print("    ");
        }
        System.out.println("}");
    }
}
