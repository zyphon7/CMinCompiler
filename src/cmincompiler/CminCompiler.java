/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmincompiler;

import CminScanner.CminusScanner;
import CminScanner.Token.TokenType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Spencer
 */
public class CminCompiler {

    /**
     * @param args the command line arguments
     */
    //Just for testing scanner
    public static void main(String[] args) {
        try{
            File cminFile = new File("CminPrograms/program1.txt");
            if(!cminFile.exists()){
                System.out.println("I dont exists!");
            }
try {
    System.out.println(cminFile.getCanonicalPath());
} catch (IOException e) {
    e.printStackTrace();
}
            CminusScanner cminscanner = new CminusScanner(new BufferedReader(new FileReader(cminFile)));
            
            while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                cminscanner.getNextToken();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        
    }
    
}
