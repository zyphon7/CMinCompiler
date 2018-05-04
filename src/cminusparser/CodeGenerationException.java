/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cminusparser;

/**
 *
 * @author annmcostantino
 */
public class CodeGenerationException extends RuntimeException{

    public CodeGenerationException() {
    }
    
    public CodeGenerationException(String e){
        super(e);
    }    
}

