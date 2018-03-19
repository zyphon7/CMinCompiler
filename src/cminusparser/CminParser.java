/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.CminusScanner;
import CminScanner.Token.TokenType;
import CminScanner.lexicalErrorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Annie
 */
public class CminParser {
    public static CminusScanner cminscanner;
    
    public static void main(String[] args) {
        try{
            File cminFile = new File("CminPrograms/program2.txt");
            cminscanner = new CminusScanner(new BufferedReader(new FileReader(cminFile)));
            
            while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                //TODO parser stuff here
            }
                
          
            
        } catch(IOException e){
            e.printStackTrace();
        } catch (lexicalErrorException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }       
    }
    public static boolean matchToken(TokenType t){
        if(cminscanner.viewNextToken().getTokenType() == t){
            cminscanner.getNextToken();
            return true;
        }
        else{
            return false;
        }
    }
}
