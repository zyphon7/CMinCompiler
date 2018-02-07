/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CminScanner;

/**
 *
 * @author Spencer & Ann
 */
public class lexicalErrorException extends Exception {

    public lexicalErrorException() {
    }
    
    public lexicalErrorException(String e){
        super(e);
    }
    
}
