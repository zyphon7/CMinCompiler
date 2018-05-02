/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

import CminScanner.Token;
import static cminusparser.CminParser.cminscanner;
import static cminusparser.CminParser.matchToken;
import static cminusparser.Expression.parseExpression;

import cminusparser.FunDecl.FunType;
import java.util.ArrayList;
import lowlevel.CodeItem;

/**
 *
 * @author Annie and Spencer
 */
public class Parameter {
    //private ArrayList<Parameter> params = new ArrayList<Parameter>();
    private String name;
    private FunType type;
    private boolean array;
    private static String caller = "PARAMETER";
    
    Parameter(){ }
    
    Parameter(FunType t){
        type = t;
        array = false;
    }
    
    protected String getName(){
        return name;
    }
    
    protected int getType(){
        if(this.type == FunType.INT){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    static ArrayList<Parameter> parseParams(){
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.VOID){
            Parameter p = new Parameter(FunType.VOID);
            matchToken(Token.TokenType.VOID, caller);
            params.add(p);
        }
        else if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.INT){
            //Some sort of while loop
            while(cminscanner.viewNextToken().getTokenType() == Token.TokenType.INT){
                Parameter p = new Parameter(FunType.INT);
                matchToken(Token.TokenType.INT, caller);
                p.name = matchToken(Token.TokenType.ID, caller).getTokenData().toString();
                //see if we have brackets
                if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.LBRACKET){
                    p.name += matchToken(Token.TokenType.LBRACKET, caller).getTokenData().toString();
                    //Don't need extra check because it should be this and if it is not
                    //matchToken will error out for us
                    p.name += matchToken(Token.TokenType.RBRACKET, caller).getTokenData().toString();
                    p.array = true;
                }
                //Can't use else if or it will skip over if we have [],
                if(cminscanner.viewNextToken().getTokenType() == Token.TokenType.COMMA){
                    matchToken(Token.TokenType.COMMA, caller);
                }
                params.add(p);
            }
        }
        else{
            //error
        }
        return params;
    }
    
    
    protected static String stringParams(ArrayList<Parameter> p){
        String params = "( ";
        for(int i = 0; i < p.size(); i++){
            Parameter param = p.get(i);
            params += param.type.toString().toLowerCase() + " ";
            if(param.name != null){
                params += param.name;
            }
            if(param.array == true){
                params += "[]";
            }
            if(i+1 != p.size()){
                params += ", ";
            }
        }
        params += " )";
        return params;
    }
    
    void print(String s){
        
    }
}
