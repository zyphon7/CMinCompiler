/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import static cminusparser.FunDecl.parseFunDecl;

/**
 *
 * @author Annie
 */
public abstract class Declaration {
    //Probably don't need type
    //String type;
    String ID;
    private static String caller = "DECLARATION";
    
    static Declaration parseDeclaration(String test){
        //matchToken(Token.TokenType.INT, caller);
        //matchToken(Token.TokenType.ID, caller);
        if(test != null){
            switch(cminscanner.viewNextToken().getTokenType()){
             case INT:
                 cminscanner.getNextToken();
                 matchToken(Token.TokenType.ID, caller);
                 parseDeclaration(null); //decl'
                 break;
             case VOID:
                 cminscanner.getNextToken();
                 matchToken(Token.TokenType.ID, caller);
                 Declaration f = parseFunDecl(null);
                 return f;
            }
        }
        else{
             /*somehow return ; and num as a decl
             use vardecl
             is something like int i; allowed? (looks like it is from our grammer)
             maybe need to have a constructor for if int isn't set
            */
        }
        
    }
    abstract void print();
}
