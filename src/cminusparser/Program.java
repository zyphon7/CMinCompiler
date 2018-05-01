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
        //for each decl call genCode
        if(decls.size() < 1){
            System.out.println("Error: No decls");
        }
        
        //keep track of the stupid linked list...
        CodeItem firstItem = getDecl(0).genCode();
        CodeItem nextItem = null;
        for(int i = 1; i < decls.size(); i++){
            if(i == 1){
                nextItem = getDecl(1).genCode();
                firstItem.setNextItem(nextItem); 
            }
            else{
                CodeItem temp = getDecl(i).genCode();
                nextItem.setNextItem(temp);
                nextItem = temp;
            }
        }
        return firstItem;
    }
}
