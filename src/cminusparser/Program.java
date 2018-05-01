/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import static cminusparser.CminParser.program;
import java.io.PrintWriter;
import java.util.ArrayList;
import lowlevel.CodeItem;

/**
 *
 * @author Annie
 */
public class Program {
    
    private ArrayList<Declaration> decls = new ArrayList<Declaration>();
    public static final String INDENT = "    ";
    
    public Program(){
        
    }
    
    void addDecl(Declaration d){
        decls.add(d);
    }
    
    public Declaration getDecl(int index){
        return decls.get(index);
    }
    
    protected void printProgram(PrintWriter w){
        w.println("PROGRAM {");
        System.out.println("PROGRAM {");
        for(int i = 0; i < decls.size(); i++){
            program.getDecl(i).print(INDENT, w);
        }
        w.println("}");
        System.out.println("}");
    }
    
    public CodeItem genLLCode(){
        return null;
    }
}
