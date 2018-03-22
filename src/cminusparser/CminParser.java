/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.CminusScanner;
import CminScanner.Token;
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
            
            
            //must have at least one decl or invalid program
            if(cminscanner.viewNextToken().getTokenType() == TokenType.INT ||
               cminscanner.viewNextToken().getTokenType() == TokenType.VOID){
                while(cminscanner.viewNextToken().getTokenType() != TokenType.EOF){
                    TokenType test = cminscanner.viewNextToken().getTokenType();
                    Declaration d = parseDeclaration(null);
                    program.addDecl(d);
                }
                //print
                program.printProgram();
            }
            else{
                System.out.println("A program must have at least one declaration.");
                System.exit(1);
            }
                   
        } catch(IOException e){
            e.printStackTrace();
        } catch (lexicalErrorException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }       
    }
    
    //Matches a token and advances the token ptr
    public static Token matchToken(TokenType t, String caller){
        if(cminscanner.viewNextToken().getTokenType() == t){
            return cminscanner.getNextToken();
        }
        else{
           System.out.println("No match for" + t.toString() + " in " + caller);
           System.exit(1);
           return null;
        }
    }
}
