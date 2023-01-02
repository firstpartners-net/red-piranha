package net.firstpartners.core;

/**
 * Simple Placeholder for Exceptions thrown by the system
 */
public class RPException extends Throwable {
    
    public RPException(){
        super();
    }

    public RPException(String message){
        super(message);
    }

    public RPException(String message, Throwable t){
        super(message,t);
    }

}
