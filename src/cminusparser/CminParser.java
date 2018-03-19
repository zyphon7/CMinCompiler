/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.CminusScanner;
import CminScanner.Token.TokenType;
import CminScanner.lexicalErrorException;
import static cminusparser.Declaration.parseDeclaration;
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
    public static Program program;
    
    public static void main(String[] args) {
        try{
            File cminFile = new File("CminPrograms/program2.txt");
            cminscanner = new CminusScanner(new BufferedReader(new FileReader(cminFile)));
            program = new Program();
            
            while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                Declaration d = parseDeclaration();
                program.addDecl(d);
            }
                   
        } catch(IOException e){
            e.printStackTrace();
        } catch (lexicalErrorException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }       
    }
    
    //Matches a token and advances the token ptr
    public static void matchToken(TokenType t, String caller){
        if(cminscanner.viewNextToken().getTokenType() == t){
            cminscanner.getNextToken();
        }
        else{
           System.out.println("No match for" + t.toString() + " in " + caller);
           System.exit(1);
        }
    }
}
